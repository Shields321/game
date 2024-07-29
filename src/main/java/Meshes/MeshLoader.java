//make this be able to have a specific place in the map and not just in the middle

package Meshes;

import usages.Face;
import usages.Vector2f;
import usages.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeshLoader {
    private List<Vector3f> vertices = new ArrayList<>();
    private List<Vector2f> textures = new ArrayList<>();
    private List<Vector3f> normals = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();
    private int[] pixels;
    private int width,height,xOffset,yOffset;

    public MeshLoader(int[] pixels, int width, int height, int xOffset, int yOffset) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    String[] tokens = line.split("\\s+");
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    vertices.add(vertex);
                } else if (line.startsWith("vt ")) {
                    String[] tokens = line.split("\\s+");
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    );
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {
                    String[] tokens = line.split("\\s+");
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    String[] tokens = line.split("\\s+");
                    int[] vertexIndices = new int[3];
                    int[] textureIndices = new int[3];
                    int[] normalIndices = new int[3];

                    for (int i = 0; i < 3; i++) {
                        String[] parts = tokens[i + 1].split("/");
                        vertexIndices[i] = Integer.parseInt(parts[0]) - 1;
                        textureIndices[i] = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) - 1 : -1;
                        normalIndices[i] = parts.length > 2 ? Integer.parseInt(parts[2]) - 1 : -1;
                    }
                    faces.add(new Face(vertexIndices, textureIndices, normalIndices));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector2f> getTextures() {
        return textures;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Face> getFaces() {
        return faces;
    }

    private void drawLine(int x0, int y0, int x1, int y1, int[] pixels, int width, int height) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            if (x0 >= 0 && x0 < width && y0 >= 0 && y0 < height) {
                pixels[y0 * width + x0] = 0xFFFFFF; // Set pixel color to white for simplicity
            }

            if (x0 == x1 && y0 == y1) break;
            int e2 = err * 2;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public int[] renderToPixels() {
        for (Face face : faces) {
            for (int i = 0; i < 3; i++) {
                Vector3f v0 = vertices.get(face.vertexIndices[i]);
                Vector3f v1 = vertices.get(face.vertexIndices[(i + 1) % 3]);

                int x0 = (int) (v0.x + xOffset);
                int y0 = (int) (v0.y + yOffset);
                int x1 = (int) (v1.x + xOffset);
                int y1 = (int) (v1.y + yOffset);

                System.out.println("Drawing line from (" + x0 + ", " + y0 + ") to (" + x1 + ", " + y1 + ")");

                drawLine(x0, y0, x1, y1, pixels, width, height);
            }
        }
        return pixels;
    }
}
