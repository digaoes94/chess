package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	
	public ChessMatch() {
		this.board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getCols()];
		
		for (int a = 0; a < board.getRows(); a++) {
			for(int b = 0; b < board.getCols(); b++) {
				mat[a][b] = (ChessPiece) board.piece(a, b);
			}
		}
		
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition before) {
		Position pos = before.toPosition();
		validateBefore(pos);
		return board.piece(pos).possibleMoves();
	}
	
	public ChessPiece movePiece(ChessPosition before, ChessPosition after) {
		Position bef = before.toPosition();
		Position af = after.toPosition();
		validateBefore(bef);
		validateAfter(bef, af);
		Piece captured = makeMove(bef, af);
		return (ChessPiece) captured;
	}
	
	private void validateBefore(Position pos) {
		if (!board.positionOccupied(pos)) {
			throw new ChessException("Original position is not occupied.");
		}
		if (!board.piece(pos).possiblePath()) {
			throw new ChessException("No possible moves for this piece.");
		}
	}
	
	private void validateAfter(Position before, Position after) {
		if (!board.piece(before).possibleMove(after)) {
			throw new ChessException("Invalid move. Choose a valid move for this piece.");
		}
	}
	
	private Piece makeMove(Position before, Position after) {
		Piece p = board.removePiece(before);
		Piece captured = board.removePiece(after);
		board.placePiece(p, after);
		return captured;
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(col, row).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('C', 1, new Rook(board, Color.WHITE));
        placeNewPiece('C', 2, new Rook(board, Color.WHITE));
        placeNewPiece('D', 2, new Rook(board, Color.WHITE));
        placeNewPiece('E', 2, new Rook(board, Color.WHITE));
        placeNewPiece('E', 1, new Rook(board, Color.WHITE));
        placeNewPiece('D', 1, new King(board, Color.WHITE));

        placeNewPiece('C', 7, new Rook(board, Color.BLACK));
        placeNewPiece('C', 8, new Rook(board, Color.BLACK));
        placeNewPiece('D', 7, new Rook(board, Color.BLACK));
        placeNewPiece('E', 7, new Rook(board, Color.BLACK));
        placeNewPiece('E', 8, new Rook(board, Color.BLACK));
        placeNewPiece('D', 8, new King(board, Color.BLACK));
	}
}
