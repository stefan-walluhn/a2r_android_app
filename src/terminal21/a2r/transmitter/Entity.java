package terminal21.a2r.transmitter;

public class Entity {
	private byte type ;
	private short value ;
	private Crc8 crc8 ;
	
	public final static byte A = (byte)0X81 ;
	public final static byte B = (byte)0X82 ;
	public final static byte C = (byte)0X83 ;
	public final static byte D = (byte)0X84 ;
	public final static byte E = (byte)0X85 ;
	public final static byte F = (byte)0X86 ;
	public final static byte ACCEL_X = (byte)0X90 ;
	public final static byte ACCEL_Y = (byte)0X91 ;
	public final static byte ACCEL_Z = (byte)0X92 ;
	public final static byte GYRO_X = (byte)0XA0 ;
	public final static byte GYRO_Y = (byte)0XA1 ;
	public final static byte GYRO_Z = (byte)0XA2 ;
	public final static byte TEMP = (byte)0XB0 ;
	public final static byte LIGHT = (byte)0XB2 ;
		
	public Entity(byte type, short value) {
		this.type = type ;
		this.value = value ;
		this.crc8 = new Crc8() ;
	}
	
	public byte getType() {
		return this.type ;
	}
	
	public byte[] toPackage() {
		byte data[] = new byte[4] ;
		data[0] = this.type ;
		data[1] = (byte)(this.value & (byte)0Xff) ;
		data[2] = (byte)((this.value >> 8) & (byte)0Xff) ;
		
		for (int i=0; i<data.length; i++) {
			crc8.update(data[i]) ;
		}
		
		data[3] = crc8.getValue() ;
		
		return data ;
	}
	
}
