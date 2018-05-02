package services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import objects.IterationPOJO;

public class IterationManagerImpl implements IterationManager {

	private RepostService repostService;
	private Map<Integer,Iteration> iterationMap;
	private SerializableLoader<List<IterationPOJO>> serializableLoader;
	private static final String PATH_TO_FILE = pathToFile();
	
	public IterationManagerImpl(RepostService repostService){
		this.repostService = repostService;
		iterationMap = new HashMap<Integer,Iteration>();
		this.serializableLoader = new SerializableLoader<List<IterationPOJO>>(PATH_TO_FILE);
	}
	
	@Override
	public void startIteration(IterationPOJO pojo) {
		pojo.setId(iterationMap.size());
		Iteration iteration = new Iteration(pojo,repostService);
		iteration.start();
		pojo.setActive(true);
		this.iterationMap.put(iterationMap.size(), iteration);
		save();
	}

	@Override
	public void delete(int id) {
		iterationMap.remove(id);
		save();
	}

	@Override
	public Map<Integer,Iteration> load() {
		if(Files.exists(Paths.get(pathToFile()))){
			this.serializableLoader.loadFile().
			forEach((IterationPOJO i) -> {iterationMap.put(i.getId(), new Iteration(i,repostService));});
		}
		return iterationMap;
	}

	@Override
	public void save() {
		List<IterationPOJO> list = new ArrayList<IterationPOJO>();
		iterationMap.values().forEach((Iteration i) -> {list.add(i.getIterationPOJO());});
		this.serializableLoader.saveFile(list);
		
	}
	
	private static String pathToFile(){
		StringBuilder path = new StringBuilder();
		path.append(System.getProperty("user.dir"));
		path.append("/saved.iterations");
		return path.toString();
	}

	public Map<Integer, Iteration> getIterationList() {
		return iterationMap;
	}

	@Override
	public void pause(int id) {
		iterationMap.get(id).kill();
		iterationMap.get(id).getIterationPOJO().setActive(false);
	}

	@Override
	public void resume(int id) {
		iterationMap.get(id).wakeUp();
		iterationMap.get(id).getIterationPOJO().setActive(true);
	}

	@Override
	public void restartIteration(IterationPOJO pojo) {
		Iteration iteration = new Iteration(pojo,repostService);
		iteration.start();
		pojo.setActive(true);
		this.iterationMap.replace(pojo.getId(), iteration);
		save();
	}
}
