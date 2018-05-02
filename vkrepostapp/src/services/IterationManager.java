package services;


import java.util.Map;

import objects.IterationPOJO;

public interface IterationManager {

	public void startIteration(IterationPOJO pojo);
	public void restartIteration(IterationPOJO pojo);
	public void delete(int id);
	public void save();
	public void pause(int id);
	public void resume(int id);
	public Map<Integer,Iteration> load();
	public Map<Integer,Iteration> getIterationList();
	
	
}
