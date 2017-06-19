package server;

public interface SocketServerClientInterface {
	public boolean openConnection();
	public void handleSession();
	public void closeSession();
}
