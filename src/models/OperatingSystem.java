package models;

import exceptions.RepeatedNameException;

import java.util.ArrayList;


public class OperatingSystem {

	private Queue<MyProcess> processQueueReady;
	private ArrayList<MyProcess> readyAndDespachado;
	private ArrayList<MyProcess> executing;
	private ArrayList<MyProcess> expired;
	private ArrayList<MyProcess> processTerminated;
	private ArrayList<MyProcess> locked;
	private ArrayList<MyProcess> toLocked;
	private ArrayList<MyProcess> lockedToReady;
	private ArrayList<MyProcess> lockedToSuspendedLocked;
	private ArrayList<MyProcess> suspendedLocked;
	private ArrayList<MyProcess> suspendedLockedToLocked;
	private ArrayList<MyProcess> suspendedLockedToSuspendedReady;
	private ArrayList<MyProcess> toSuspendedReady;
	private ArrayList<MyProcess> suspendedReady;
	private ArrayList<MyProcess> suspendedReadyToReady;
	

	public OperatingSystem() {
		this.processQueueReady = new Queue<>();
		this.readyAndDespachado = new ArrayList<>();
		this.executing = new ArrayList<>();
		this.expired = new ArrayList<>();
		this.processTerminated = new ArrayList<>();
		this.locked =new ArrayList<>();
		this.toLocked =new ArrayList<>();
		this.lockedToReady = new ArrayList<>();
		this.lockedToSuspendedLocked =new ArrayList<>();
		this.suspendedLocked =new ArrayList<>();
		this.suspendedLockedToLocked =new ArrayList<>();
		this.suspendedLockedToSuspendedReady = new ArrayList<>();
		this.toSuspendedReady=new ArrayList<>();
		this.suspendedReady =new ArrayList<>();
		this.suspendedReadyToReady =new ArrayList<>();
	}

	public boolean addProcess(MyProcess myProcess) {
		if (search(myProcess.getName()) == null) {
			processQueueReady.push(myProcess);
			addProcess(readyAndDespachado,myProcess, false);
			return true;
		}
		return false;
	}

	/**
	 * Me avisa si no funciona, xd
	 * @param actualName
	 * @param name
	 * @param time
	 * @param lockedStatus
	 */
	public void editProcess(String actualName, String name, int time, boolean... states) {
		edit(search(actualName), name, time, states);
		edit(searchInList(actualName, readyAndDespachado), name, time, states);
	}
	
	private void edit(MyProcess myProcess, String name, int time, boolean ... states) {
		myProcess.setName(name);
		myProcess.updateTime(time);
		myProcess.setLocked(states[0]);
		myProcess.setSuspended_Locked(states[1]);
		myProcess.setSuspended_Ready(states[2]);
	}
	
	/**
	 * Eliminar de la cola y de la lista de listos
	 * @param name
	 * @return
	 */
	public boolean deleteProccess(String name) {
		boolean isDelete = false;
		Node<MyProcess> temp = processQueueReady.peek();
		readyAndDespachado.remove(searchInList(name, readyAndDespachado));
		if (temp.getData().getName().equals(name)) {
			processQueueReady.pop();
			isDelete = true;
		}else {
			isDelete = deleteProcess(name, isDelete, temp);
		}	
		return isDelete;
	}

	private boolean deleteProcess(String name, boolean isDelete, Node<MyProcess> temp) {
		while (temp.getNext() != null) {
			if (temp.getNext().getData().getName().equals(name)) {
				temp.setNext(temp.getNext().getNext());
				isDelete = true;
			} else {
				temp = temp.getNext();
			}
		}
		return isDelete;
	}

	private MyProcess searchInList(String name, ArrayList<MyProcess> myProcesses) {
		for (MyProcess myProcess : myProcesses) {
			if (name.equals(myProcess.getName())) {
				return myProcess;
			}
		}
		return null;
	}
	
	
	public MyProcess search(String name) {
		Node<MyProcess> temp = processQueueReady.peek();
		while (temp != null) {
			if (temp.getData().getName().equals(name)) {
				return temp.getData();
			} else {
				temp = temp.getNext();
			}
		}
		return null;
	}

	public void startSimulation() {
		while (!processQueueReady.isEmpty()) {
			MyProcess process = processQueueReady.peek().getData();
			valideSystemTimer(process);
		}
	}

	private void valideSystemTimer(MyProcess process) {
		addProcess(executing, process, true);
		if ((process.getTime() - 5) > 0) {
			proccessTimeDiscount(process);
		} else {
			MyProcess myProcess = processQueueReady.pop();
			myProcess.setTime((int)myProcess.getTime());
			addProcess(processTerminated, myProcess, false);
		}
	}

	private void proccessTimeDiscount(MyProcess process) {
		process.setTime(5);
		valideLocked(process);
//		readyAndDespachado.add(new MyProcess(process.getName(), process.getTime(), process.isLocked()));
//		processQueueReady.push(processQueueReady.pop());
	}

	private void valideLocked(MyProcess process) {
		if (process.isLocked()) {
			addProcess(toLocked, process, false);
			addProcess(locked,process,false);
			if (process.isSuspended_Locked()) {
				addProcess(lockedToSuspendedLocked, process, false);
				addProcess(suspendedLocked, process, false);
				if(process.isSuspended_Ready()) {
					addProcess(suspendedLockedToSuspendedReady, process, false);
					addProcess(suspendedReady, process, false);
					addProcess(suspendedReadyToReady, process, false);
					addToQueue(process);
				}else {
					addProcess(suspendedLockedToLocked, process, false);
					addProcess(locked, process, false);
					addProcess(lockedToReady, process, false);
					addToQueue(process);
				}
			}else if(process.isSuspended_Ready()){
				addProcess(lockedToSuspendedLocked, process, false);
				addProcess(suspendedLocked, process, false);
				addProcess(suspendedLockedToSuspendedReady, process, false);
				addProcess(suspendedReady, process, false);
				addProcess(suspendedReadyToReady, process, false);
				addToQueue(process);
			}else{
				addProcess(lockedToReady, process, false);
				addToQueue(process);
			}
		}else if (process.isSuspended_Locked()) {
			addProcess(toLocked, process, false);
			addProcess(locked,process,false);
			addProcess(lockedToSuspendedLocked, process, false);
			addProcess(suspendedLocked, process, false);
			if(process.isSuspended_Ready()) {
				addProcess(suspendedLockedToSuspendedReady, process, false);
				addProcess(suspendedReady, process, false);
				addProcess(suspendedReadyToReady, process, false);
				addToQueue(process);
			}else {
				addProcess(suspendedLockedToLocked, process, false);
				addProcess(locked, process, false);
				addProcess(lockedToReady, process, false);
				addToQueue(process);
			}
		}else if(process.isSuspended_Ready()) {
			addProcess(toSuspendedReady, process, false);
			addProcess(suspendedReady, process, false);
			addProcess(suspendedReadyToReady, process, false);
			addToQueue(process);
		}else {
			addProcess(expired, process, false);
			addToQueue(process);
		}
	}

	private void addToQueue(MyProcess process ) {
		addProcess(readyAndDespachado, process, false);
		MyProcess myProcess= processQueueReady.pop();
		processQueueReady.push(myProcess);
	}

	
	private void addProcess(ArrayList<MyProcess> myProcesses, MyProcess myProcess, boolean isExecuting) {
		boolean[] states = new boolean[] { myProcess.isLocked(), myProcess.isSuspended_Locked(), myProcess.isSuspended_Ready()};
		if (isExecuting) {
			myProcesses
					.add(new MyProcess(myProcess.getName(), (myProcess.getTime() - 5 < 0 ? 0 : myProcess.getTime() - 5), states));
		} else {
			myProcesses.add(new MyProcess(myProcess.getName(), myProcess.getTime(), states));
		}
	}
	
	/**
	 * 
	 * @return Los procesos que se van agregando a la lista, estos toca ir actualizando
	 * cada que se agregan a la interfaz
	 */
	public Queue<MyProcess> getProcessQueue() {
		return processQueueReady;
	}

	public void verifyProcessName(String name) throws RepeatedNameException {
		Node<MyProcess> temp = processQueueReady.peek();
		while(temp != null){
			if(temp.getData().getName().equals(name)){
				throw new RepeatedNameException(name);
			}
			temp = temp.getNext();
		}
	}

/**
 * 
 * @return listos  y despachados
 */
	public ArrayList<MyProcess> getReadyAndDespachado() {
		return readyAndDespachado;
	}
	
	/**
	 * 
	 * @return en ejecucion
	 */
	public ArrayList<MyProcess> getExecuting() {
		return executing;
	}
	
	/**\
	 * 
	 * @return expirados
	 */
	public ArrayList<MyProcess> getExpired() {
		return expired;
	}
	
	/**
	 * 
	 * @return terminados
	 */
	public ArrayList<MyProcess> getProcessTerminated() {
		return processTerminated;
	}
	
	/**
	 * 
	 * @return a bloqueado
	 */
	public ArrayList<MyProcess> getToLocked() {
		return toLocked;
	}
	
	/**
	 * 
	 * @return bloqueados
	 */
	public ArrayList<MyProcess> getLocked() {
		return locked;
	}
	
	/**
	 * 
	 * @return de bloqueado a listo
	 */
	public ArrayList<MyProcess> getLockedToReady() {
		return lockedToReady;
	}
	
	/**
	 * 
	 * @return de bloqueado a suspendido listo
	 */
	public ArrayList<MyProcess> getLockedToSuspendedLocked() {
		return lockedToSuspendedLocked;
	}
	
	/**
	 * 
	 * @return suspendidos bloqueados
	 */
	public ArrayList<MyProcess> getSuspendedLocked() {
		return suspendedLocked;
	}
	
	/**
	 * 
	 * @return suspendido bloqueado a bloqueado
	 */
	public ArrayList<MyProcess> getSuspendedLockedToLocked() {
		return suspendedLockedToLocked;
	}
	
	/**
	 * 
	 * @return suspendido bloqueado a suspendido listo
	 */
	public ArrayList<MyProcess> getSuspendedLockedToSuspendedReady() {
		return suspendedLockedToSuspendedReady;
	}
	
	/**
	 * 
	 * @return de ejecucion a suspendido listo
	 */
	public ArrayList<MyProcess> getToSuspendedReady() {
		return toSuspendedReady;
	}
	
	/**
	 * 
	 * @return suspendiddo listo
	 */
	public ArrayList<MyProcess> getSuspendedReady() {
		return suspendedReady;
	}
	
	/**
	 * 
	 * @return suspendido listo a listo
	 */
	public ArrayList<MyProcess> getSuspendedReadyToReady() {
		return suspendedReadyToReady;
	}
	
	public void show() {
		System.out.println("Listos y despachados");
		for (MyProcess myProcess : readyAndDespachado) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("En ejecucion");
		for (MyProcess myProcess : executing) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Expirados");
		for (MyProcess myProcess : expired) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("A bloqueo");
		for (MyProcess myProcess : toLocked) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Bloqueados");
		for (MyProcess myProcess : locked) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("De bloqueado a listo");
		for (MyProcess myProcess : lockedToReady) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("De bloqueado a suspendido bloqueado");
		for (MyProcess myProcess : lockedToSuspendedLocked) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Suspendido bloqueado");
		for (MyProcess myProcess : suspendedLocked) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Suspendido bloqueado a bloqueado");
		for (MyProcess myProcess : suspendedLockedToLocked) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Suspendido bloqueado a suspendido listo");
		for (MyProcess myProcess : suspendedLockedToSuspendedReady) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("A Suspendido listo");
		for (MyProcess myProcess : toSuspendedReady) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Suspendido listo");
		for (MyProcess myProcess : suspendedReady) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Suspendido listo a listo");
		for (MyProcess myProcess : suspendedReadyToReady) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
		
		System.out.println("Terminados");
		for (MyProcess myProcess : processTerminated) {
			System.out.println(myProcess.getName()+" "+ myProcess.getTime());
		}
	}
	
	public static Object[][] processInfo(ArrayList<MyProcess> processes){
		Object[][] processInfo = new Object[processes.size()][3];
		for (int i = 0; i < processes.size(); i++) {
			processInfo[i][0] = processes.get(i).getName();
			processInfo[i][1] = processes.get(i).getTime();
			processInfo[i][2] = processes.get(i).isLocked();
		}
		return processInfo;
	}
	
	public static void main(String[] args) {
		OperatingSystem operatingSystem = new OperatingSystem();
		operatingSystem.addProcess(new MyProcess("P1", 25, new boolean[] {true, true, true} ));
		operatingSystem.addProcess(new MyProcess("P2", 15, new boolean[] {false, true, false} ));
		operatingSystem.addProcess(new MyProcess("P3", 21, new boolean[] {false, false, true} ));
		operatingSystem.addProcess(new MyProcess("P4", 13, new boolean[] {true, false, false} ));
		operatingSystem.addProcess(new MyProcess("P5", 22, new boolean[] {true, true, false} ));
		operatingSystem.addProcess(new MyProcess("P6", 10, new boolean[] {false, true, true} ));
		operatingSystem.addProcess(new MyProcess("P7", 15, new boolean[] {true, false, true} ));
		operatingSystem.addProcess(new MyProcess("P8", 25, new boolean[] {false, false, false} ));
		
		operatingSystem.startSimulation();
		
		operatingSystem.show();
	}
}
