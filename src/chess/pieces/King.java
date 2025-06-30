package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position pos) {
		ChessPiece p = (ChessPiece) getBoard().piece(pos);
		return p == null || p.getColor() != this.getColor();
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];
		
		Position p = new Position(0, 0);
		// UP
		p.setValues(pos.getRow() - 1, pos.getCol());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// UP-RIGHT
		p.setValues(pos.getRow() - 1, pos.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// RIGHT
		p.setValues(pos.getRow(), pos.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// DOWN-RIGHT
		p.setValues(pos.getRow() + 1, pos.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// DOWN
		p.setValues(pos.getRow() + 1, pos.getCol());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// DOWN-LEFT
		p.setValues(pos.getRow() + 1, pos.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// LEFT
		p.setValues(pos.getRow(), pos.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// LEFT-UP
		p.setValues(pos.getRow() - 1, pos.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		return mat;
	}
}