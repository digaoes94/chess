package chess.exceptions;

import boardgame.exceptions.BoardException;

public class ChessException extends BoardException {
	private static final long serialVersionUID = 1L;
	
	public ChessException(String msg) {
		super(msg);
	}
}
