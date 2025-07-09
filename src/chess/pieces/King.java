package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	private ChessMatch match;

	public King(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position pos) {
		ChessPiece p = (ChessPiece) getBoard().piece(pos);
		return p == null || p.getColor() != this.getColor();
	}
	
	private boolean testCastling(Position ableRook) {
		ChessPiece p = (ChessPiece) getBoard().piece(ableRook);
		return p != null && p instanceof Rook && p.getColor() == this.getColor() && p.getMoveCount() == 0;
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
		
		// CASTLING
		if(getMoveCount() == 0 && !match.getCheck()) {
			// CASTLING CLOSE
			Position closeRook = new Position(pos.getRow(), pos.getCol() + 3);
			if(testCastling(closeRook)) {
				Position kingRight1 = new Position(pos.getRow(), pos.getCol() + 1);
				Position kingRight2 = new Position(pos.getRow(), pos.getCol() + 2);
				if(getBoard().piece(kingRight1) == null && getBoard().piece(kingRight2) == null) {
					mat[pos.getRow()][pos.getCol() + 2] = true;
				}
			}
			
			// CASTLING FAR
			Position farRook = new Position(pos.getRow(), pos.getCol() - 4);
			if(testCastling(farRook)) {
				Position kingLeft1 = new Position(pos.getRow(), pos.getCol() - 1);
				Position kingLeft2 = new Position(pos.getRow(), pos.getCol() - 2);
				Position kingLeft3 = new Position(pos.getRow(), pos.getCol() - 3);
				if(getBoard().piece(kingLeft1) == null && getBoard().piece(kingLeft2) == null && getBoard().piece(kingLeft3) == null) {
					mat[pos.getRow()][pos.getCol() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}