package Gui;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.*;
import Game.Game_Main;
import player.Player;

public class Camera implements KeyListener, MouseMotionListener {
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;
    public Game_Main game;
    public Player player;

    private double sensitivity = .002; // Adjust sensitivity as needed
    private int centerX, centerY;
    private int prevX, prevY;
    private boolean isMouseLocked = true;
    private Robot robot;

    public Camera(double x, double y, double xd, double yd, double xp, double yp, Game_Main game, Player player) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
        this.game = game;
        this.player = player;

        // Center of the screen
        centerX = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        centerY = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2;

        prevX = centerX;
        prevY = centerY;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {}



    @Override
    public void keyPressed(KeyEvent key) {
        if (player.getStamina() > 0) {
            if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
                left = true;
                game.resetStaminaRecoveryTask();
            }
            if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
                right = true;
                game.resetStaminaRecoveryTask();
            }
            if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
                forward = true;
                game.resetStaminaRecoveryTask();
            }
            if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
                back = true;
                game.resetStaminaRecoveryTask();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ALT) {
            isMouseLocked = !isMouseLocked;
            if (isMouseLocked) {
                lockMouse();
            } else {
                unlockMouse();
            }
        }
        if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
        if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
            game.updateStaminaAfterForwardMovement();
            forward = false;
        }
        if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
            back = false;
        }
    }

    public void update(int[][] map) {
        if (forward) {
            if (map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos += xDir * MOVE_SPEED;
            }
            if (map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0) {
                yPos += yDir * MOVE_SPEED;
            }
        }
        if (back) {
            if (map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos -= xDir * MOVE_SPEED;
            if (map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)] == 0)
                yPos -= yDir * MOVE_SPEED;
        }
        if (right) {
            // Move the player position in the right direction
            if (map[(int)(xPos + yDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos += yDir * MOVE_SPEED;
            }
            if (map[(int)xPos][(int)(yPos - xDir * MOVE_SPEED)] == 0) {
                yPos -= xDir * MOVE_SPEED;
            }
        }
        if (left) {
            // Move the player position in the left direction
            if (map[(int)(xPos - yDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos -= yDir * MOVE_SPEED;
            }
            if (map[(int)xPos][(int)(yPos + xDir * MOVE_SPEED)] == 0) {
                yPos += xDir * MOVE_SPEED;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isMouseLocked) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            // Calculate the change in mouse position
            double dx = (mouseX - centerX) * sensitivity;
            double dy = (mouseY - centerY) * sensitivity;

            // Apply rotation
            rotate(dx, dy);

            // Recenter the mouse
            try {
                robot.mouseMove(centerX, centerY);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void rotate(double yaw, double pitch) {
        // Horizontal (yaw) rotation
        double oldXDir = xDir;
        xDir = xDir * Math.cos(-yaw) - yDir * Math.sin(-yaw);
        yDir = oldXDir * Math.sin(-yaw) + yDir * Math.cos(-yaw);

        double oldXPlane = xPlane;
        xPlane = xPlane * Math.cos(-yaw) - yPlane * Math.sin(-yaw);
        yPlane = oldXPlane * Math.sin(-yaw) + yPlane * Math.cos(-yaw);

        // Vertical (pitch) rotation
        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);
        xDir = xDir * cosPitch - yDir * sinPitch;
        yDir = yDir * cosPitch + xDir * sinPitch;

        xPlane = xPlane * cosPitch - yPlane * sinPitch;
        yPlane = yPlane * cosPitch + xPlane * sinPitch;
    }

    private void lockMouse() {
        try {
            robot.mouseMove(centerX, centerY);
            // Hide the cursor
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                    new MouseEvent(new Component() {}, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, centerX, centerY, 0, false)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void unlockMouse() {
        // Show the cursor again
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                new MouseEvent(new Component() {}, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, -1, -1, 0, false)
        );
    }
}
