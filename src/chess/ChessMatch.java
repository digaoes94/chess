package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
	private List<Piece> piecesAvaible = new ArrayList<Piece>();
	private List<Piece> piecesCaptures = new ArrayList<Piece>();
	
	public ChessMatch() {
		this.board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
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
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
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
		nextTurn();
		return (ChessPiece) captured;
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void validateBefore(Position pos) {
		if (!board.positionOccupied(pos)) {
			throw new ChessException("Original position is not occupied.");
		}
		if (currentPlayer != ((ChessPiece) board.piece(pos)).getColor()) {
			throw new ChessException("You can only move your own pieces. Choose wisely.");
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
		if (captured != null) {
			piecesAvaible.remove(captured);
			piecesCaptures.add(captured);
		}
		return captured;
	}
	
	private void undoCheck(Position before, Position after, Piece captured) {
		Piece p = board.removePiece(after);
		board.placePiece(p, before);
		if (captured != null) {
			board.placePiece(p, after);
			piecesCaptures.remove(captured);
			piecesAvaible.add(captured);
		}
	}
	
	private Color enemy(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesAvaible.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
		
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		
		throw new IllegalStateException("King of color " + color + " isn't on the board.");
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(col, row).toPosition());
		piecesAvaible.add(piece);
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
