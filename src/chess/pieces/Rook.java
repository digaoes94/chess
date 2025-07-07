package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];
		Position p = new Position(0, 0);
		
		// above
		p.setValues(pos.getRow() - 1, pos.getCol());
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setRow(p.getRow() - 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// left
		p.setValues(pos.getRow(), pos.getCol() - 1);
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setCol(p.getCol() - 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// right
		p.setValues(pos.getRow(), pos.getCol() + 1);
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setCol(p.getCol() + 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		// below
		p.setValues(pos.getRow() + 1, pos.getCol());
		while (getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
			mat[p.getRow()][p.getCol()] = true;
			p.setRow(p.getRow() + 1);
		}
		if (getBoard().positionExists(p) && enemyAt(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		
		return mat;
	}
}