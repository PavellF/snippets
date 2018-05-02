package theCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The Command Pattern encapsulates a request as an object, 
 * thereby letting you parameterize other objects with different 
 * requests, queue or log requests, and support undoable operations.
 * 
 **/

public class Client {

	public static void main(String[] args) {
		
		WriterService ws = new WriterServiceImpl();
		FontSizeService fss = new FontSizeServiceImpl();
		EditorService es = new EditorServiceImpl();
		
		EditorWindow ew = new EditorWindow();
		ew.setActionPanelButtons(new BoldApplyButtonLogic(es));
		ew.setActionPanelButtons(new BoldClearButtonLogic(es));
		
		ew.setActionPanelButtons(new TextUndoButton(ws));
		
		ew.setActionPanelButtons(new FontSizeButtonUpLogic(fss));
		ew.setActionPanelButtons(new FontSizeDownButtonLogic(fss));
		
		ew.onClick(0, null);
		ew.onClick(1, null);
		ew.undo();
		
		ew.onClick(2, null);
		ew.undo();
		
		ew.onClick(3, 33);
		ew.onClick(4, 23);
		ew.undo();
		
		ew.loadAll();
		
		List<Command<Integer>> commands = new ArrayList<>();
		commands.add(new BoldApplyButtonLogic(es));
		commands.add(new FontSizeButtonUpLogic(fss));
		
		Command<Integer> strong = new MacroCommand<>(commands);
		
		ew.setActionPanelButtons(strong);
		
		ew.onClick(5, 100);
		ew.undo();
		
		System.out.println(ew.toString());
		
		ew.onClick(3, 33);
		ew.onClick(4, 23);
		
		System.out.println(ew.toString());
		
	}

}
