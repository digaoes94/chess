package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];
		Position p = new Position(0, 0);
		
		//white pawn
		if(this.getColor() == Color.WHITE) {
			//normal move
			p.setValues(pos.getRow() - 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//first move
			p.setValues(pos.getRow() - 2, pos.getCol());
			Position p2 = new Position(pos.getRow() - 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p) && 
			   getBoard().positionExists(p2) && !getBoard().positionOccupied(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//eat left
			p.setValues(pos.getRow() - 1, pos.getCol() - 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}

			//eat right
			p.setValues(pos.getRow() - 1, pos.getCol() + 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
		}
		else {
			//normal move
			p.setValues(pos.getRow() + 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//first move
			p.setValues(pos.getRow() + 2, pos.getCol());
			Position p2 = new Position(pos.getRow() + 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().positionOccupied(p) && 
			   getBoard().positionExists(p2) && !getBoard().positionOccupied(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getCol()] = true;
			}
			
			//eat right
			p.setValues(pos.getRow() + 1, pos.getCol() - 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}

			//eat left
			p.setValues(pos.getRow() + 1, pos.getCol() + 1);
			if(getBoard().positionExists(p) && enemyAt(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
		}
		
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	
}