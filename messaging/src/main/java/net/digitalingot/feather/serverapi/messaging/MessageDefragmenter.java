package net.digitalingot.feather.serverapi.messaging;

public class MessageDefragmenter {
    private final short count;
    private final byte[] buffer;
    private int offset = 0;
    private int expectedFragment = 0;

    public MessageDefragmenter(SimpleMessageBuffer buffer) {
        this.count = buffer.readUnsignedByte();
        int size = buffer.readVarInt();
        if (size > MessageFragmentationConstants.MAX_SIZE) {
            throw new RuntimeException("Exceeds size");
        }
        this.buffer = new byte[size];
    }

    public boolean decode(SimpleMessageBuffer buffer) {
        short fragment = buffer.readUnsignedByte();
        if (fragment != this.expectedFragment) {
            throw new RuntimeException("Unexpected fragment");
        }
        int remaining = buffer.remaining();
        buffer.readBytes(this.buffer, this.offset, remaining);
        this.offset += remaining;
        this.expectedFragment++;
        return this.isComplete();
    }

    public boolean isComplete() {
        return this.expectedFragment == this.count;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }
}
