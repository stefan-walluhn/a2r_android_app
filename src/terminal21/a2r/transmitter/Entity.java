package terminal21.a2r.transmitter;

public class Entity {
	private byte type ;
	private short value ;
	private Crc8 crc8 ;
	
	public final static byte X = (byte)0X81 ;
	public final static byte Y = (byte)0X82 ;
	public final static byte ACCEL_X = (byte)0X90 ;
	public final static byte ACCEL_Y = (byte)0X91 ;
	public final static byte ACCEL_Z = (byte)0X92 ;
	public final static byte GYR_X = (byte)0XA0 ;
	public final static byte GYR_Y = (byte)0XA1 ;
	public final static byte GYR_Z = (byte)0XA2 ;
	
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
