package terminal21.a2r.transmitter;

public class Entity {
	private byte type ;
	private short value ;
	private Crc8 crc8 ;
	
	public final static byte X = (byte)0X81 ;
	public final static byte Y = (byte)0X82 ;
	
	public Entity(byte type, short value) {
		this.type = type ;
		this.value = value ;
		this.crc8 = new Crc8() ;
	}
	
	public byte[] toPackage() {
		byte data[] = new byte[4] ;
		data[0] = this.type ;
		data[1] = (byte)(this.value & 0xff) ;
		data[2] = (byte)((this.value >> 8) & 0xff) ;
		
		for (int i=0; i<data.length; i++) {
			crc8.update(data[i]) ;
		}
		
		data[3] = crc8.getValue() ;
		
		return data ;
	}
	
}
