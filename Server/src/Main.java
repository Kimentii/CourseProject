import java.net.*;
import java.io.*;

public class Main {
	static Game game;
	static final int PORT = 6666;

	Main() {
		game = new Game();
		game.start();
	}

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT); // ������� �����
																// ������� �
																// �����������
																// ��� �
																// ��������������
																// �����
			System.out.println("Waiting for a client...");

			Socket socket = serverSocket.accept(); // ���������� ������ �����
													// ����������� � �������
													// ��������� ����� ���-��
													// �������� � ��������
			System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
			System.out.println();

			// ����� ������� � �������� ������ ������, ������ ����� �������� �
			// �������� ������ �������.
			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();

			// ������������ ������ � ������ ���, ���� ����� ������������
			// ��������� ���������.
			DataInputStream in = new DataInputStream(sin);
			DataOutputStream out = new DataOutputStream(sout);
			out.writeUTF("X");

			// ����������� ������� ������
			System.out.println("Waiting for a client...");
			Socket socket2 = serverSocket.accept();
			System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
			System.out.println();

			// ����� ������� � �������� ������ ������, ������ ����� �������� �
			// �������� ������ �������.
			InputStream sin2 = socket2.getInputStream();
			OutputStream sout2 = socket2.getOutputStream();

			// ������������ ������ � ������ ���, ���� ����� ������������
			// ��������� ���������.
			DataInputStream in2 = new DataInputStream(sin2);
			DataOutputStream out2 = new DataOutputStream(sout2);
			out2.writeUTF("O");

			String line, line2;
			int x, y, x2, y2;
			while (true) {
				while (true) {
					line = in.readUTF();
					System.out.println("First client: " + line);
					out2.writeUTF(line);
					x = Integer.parseInt(line);
					line = in.readUTF();
					System.out.println("First client: " + line);
					out2.writeUTF(line);
					y = Integer.parseInt(line);
	                if (game.makeTurn(x, y)) {
	                    break;
	                }
				}

				while(true)
				{
					line2 = in2.readUTF();
					out.writeUTF(line2);
					x = Integer.parseInt(line2);
					System.out.println("Second client: " + line2);
					line2 = in2.readUTF();
					System.out.println("Second client: " + line2);
					out.writeUTF(line2);
					y = Integer.parseInt(line2);
	                if (game.makeTurn(x, y)) {
	                    break;
	                }
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}

	}

}
