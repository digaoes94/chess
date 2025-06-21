package chess;

import boardgame.Position;
import chess.exceptions.ChessException;

public class ChessPosition {
	private char col;
	private int row;
	
	
	public ChessPosition(char col, int row) {
		if(Character.isLowerCase(col)) {
			col = Character.toUpperCase(col);
		}
		
		if (col < 'A' || col > 'H' || row < 1 || row > 8) {
			throw new ChessException("Invalid position, must be between A1 and H8");
		}
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}
	public char getCol() {
		return col;
	}
	
	protected Position toPosition() {
		return new Position(8 - row, col - 'A');
	}
	
	protected static ChessPosition fromPosition(Position pos) {
		return new ChessPosition((char)('A' - pos.getCol()), 8 - pos.getRow());
	}

	@Override
	public String toString() {
		return "" + col + row;
	}
	
	
}
