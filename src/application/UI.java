package application;

import chess.ChessPiece;

public class UI {
	public static void printBoard(ChessPiece[][] pieces) {
		for (int a = 0; a < pieces.length; a++) {
			System.out.print((8 - a) + " ");
			
			for(int b = 0; b < pieces.length; b++) {
				printPiece(pieces[a][b]);
			}
			System.out.println();
		}
		
		System.out.println("  A B C D E F G H");
	}
	
	private static void printPiece(ChessPiece piece) {
		if(piece == null) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		
		System.out.print(" ");
	}
}
