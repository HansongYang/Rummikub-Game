package core;

public interface PlayerStrategy <T extends Player>{
	
	public void executeStrategy(T player);
}
