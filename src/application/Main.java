package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		ChessMatch match = new ChessMatch();
		
		while (true) {
			UI.printBoard(match.getPieces());
			System.out.println();
			System.out.print("Source: ");
			ChessPosition before = UI.readChessPosition(scan);
			
			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(scan);
			
			ChessPiece captured = match.movePiece(before, target);
		}
		

	}
}