import java.io.*;

public class ByteStream {
    private OutputStream stream;

    public ByteStream(OutputStream stream) {
        this.stream = stream;
    }

    public void writeVInt(int value) throws IOException {
        while ((value & ~0x7F) != 0) {
            stream.write((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        stream.write(value & 0x7F);
    }

    public void writeInt(int value) throws IOException {
        stream.write(value >> 24);
        stream.write(value >> 16);
        stream.write(value >> 8);
        stream.write(value);
    }

    public void writeString(String value) throws IOException {
        byte[] bytes = value.getBytes();
        writeVInt(bytes.length);
        stream.write(bytes);
    }

    public void writeByte(byte value) throws IOException {
        stream.write(value);
    }

    public void writeHexa(int value) throws IOException {
        writeString(Integer.toHexString(value));
    }

    public void writeUnsignedInt(long value) throws IOException {
        writeInt((int) (value & 0xFFFFFFFF));
    }

    public void writeBoolean(boolean value) throws IOException {
        stream.write(value ? 1 : 0);
    }
}
