package usages;

public class Face {
    public int[] vertexIndices;
    int[] textureIndices;
    int[] normalIndices;

    public Face(int[] vertexIndices, int[] textureIndices, int[] normalIndices) {
        this.vertexIndices = vertexIndices;
        this.textureIndices = textureIndices;
        this.normalIndices = normalIndices;
    }
}

