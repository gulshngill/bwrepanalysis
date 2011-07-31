package belicza.andras.bwhf.control;

/**
 * Refers to an error parsing a replay text.
 * 
 * @author Andras Belicza
 */
public class UnitIDException extends Exception {
	
	/**
	 * Creates a new ParseException.<br>
	 * This constructor should be used if the parser fails to read the source.
	 */
	public UnitIDException() {
		super( "Error by converting id" );
	}
	
	/**
	 * Creates a new ParseException.<br>
	 * This constructor should be used if the source contains invalid content.
	 * @param line line where the parse failed
	 */
	public UnitIDException( final int code, final int id ) {
		super( "Error convertiong from code: " + code + " to id: " + id);
	}
	
}
