package week8.기계식주차장;

import java.util.*;

class Car implements Comparable<Car> {
	String carNo;
	int xx;
	char y;
	int zzzz;
	int status; // 0: 주차, 1: 견인, 2: 완전 삭제(출고/재입차)
	int enterTime;
	int row, col;

	Car(String no, int time, int r, int c) {
		this.carNo = no;
		this.xx = Integer.parseInt(no.substring(0,2));
		this.y = no.charAt(2);
		this.zzzz = Integer.parseInt(no.substring(3,7));
		this.status = 0;
		this.enterTime = time;
		this.row = r;
		this.col = c;
	}

	@Override
	public int compareTo(Car o) {
		// 이미 주차(parked), 견인(towed) 큐를 따로 두었기 때문에 상태(status) 비교는 생략
		if (this.xx != o.xx)
			return this.xx - o.xx;
		if (this.y != o.y)
			return this.y - o.y;
		return this.enterTime - o.enterTime; // 완벽히 동일할 경우 먼저 들어온 차 우선
	}
}

// ZZZZ별로 주차된 차와 견인된 차를 따로 관리하는 그룹
class ZZZZGroup {
	PriorityQueue<Car> parked = new PriorityQueue<>();
	PriorityQueue<Car> towed = new PriorityQueue<>();
}

class UserSolution {
	int n, m, l;

	int[] emptyCount;
	PriorityQueue<Integer>[] emptySlots;
	Queue<Car> presentCars;
	Map<String, Car> carMap;

	ZZZZGroup[] zzzzMap;

	public void init(int N, int M, int L) {
		n = N;
		m = M;
		l = L;

		emptyCount = new int[n];
		emptySlots = new PriorityQueue[n];

		for (int i = 0; i < n; i++) {
			emptyCount[i] = m;
			emptySlots[i] = new PriorityQueue<>();
			for (int j = 0; j < m; j++) {
				emptySlots[i].add(j);
			}
		}

		presentCars = new ArrayDeque<>();
		carMap = new HashMap<>();
		// 0000 ~ 9999 까지 존재
		zzzzMap = new ZZZZGroup[10000];
	}

	void simulate(int t) {
		while (!presentCars.isEmpty()) {
			if (presentCars.peek().enterTime + l <= t) {
				Car car = presentCars.poll();

				if (car.status == 0) {
					car.status = 1; // 상태만 1로 변경 (Lazy Evaluation)
					emptySlots[car.row].add(car.col);
					emptyCount[car.row]++;
				}
			} else {
				break;
			}
		}
	}

	void removeCarData(Car car) {
		car.status = 2;
		carMap.remove(car.carNo);
	}

	public Solution.RESULT_E enter(int mTime, String mCarNo) {
		simulate(mTime);
		Solution.RESULT_E res = new Solution.RESULT_E();

		// 견인된 차량이 재입차할 수 있다. 이런 경우도 사용자가 이미 견인된 차량을 차량 보관소에서 찾았다고 생각하여 기록을 삭제한다.
		// 만약, 견인된 차량 번호가 전달된 경우 주차 성공 여부와 상관없이 견인된 기록이 삭제되고 더 이상 견인된 차량으로 생각하지 않는다.
		if (carMap.containsKey(mCarNo)) {
			Car oldCar = carMap.get(mCarNo);
			if (oldCar.status == 1) {
				removeCarData(oldCar);
			}
		}

		int bestX = -1;
		int maxEmpty = 0;

		for (int i = 0; i < n; i++) {
			if (emptyCount[i] > maxEmpty) {
				maxEmpty = emptyCount[i];
				bestX = i;
			}
		}

		if (bestX == -1) {
			res.success = 0;
			return res;
		}

		int bestY = emptySlots[bestX].poll();
		emptyCount[bestX]--;

		Car newCar = new Car(mCarNo, mTime, bestX, bestY);
		carMap.put(mCarNo, newCar);

		if (zzzzMap[newCar.zzzz] == null) {
			zzzzMap[newCar.zzzz] = new ZZZZGroup();
		}
		zzzzMap[newCar.zzzz].parked.add(newCar); // 무조건 parked 큐에 삽입

		presentCars.add(newCar);

		res.success = 1;
		res.locname = (char) (bestX + 'A') + (String.format("%03d", bestY));

		return res;
	}

	public int pullout(int mTime, String mCarNo) {
		simulate(mTime);

		if (!carMap.containsKey(mCarNo))
			return -1;

		Car car = carMap.get(mCarNo);
		int result = -1;

		if (car.status == 0) {
			result = mTime - car.enterTime;
			emptySlots[car.row].add(car.col);
			emptyCount[car.row]++;
		} else if (car.status == 1) {
			int towTime = mTime - (car.enterTime + l);
			result = -1 * (l + towTime * 5);
		}

		removeCarData(car);
		return result;
	}

	public Solution.RESULT_S search(int mTime, String mStr) {
		simulate(mTime);
		Solution.RESULT_S res = new Solution.RESULT_S();

		int zIdx = Integer.parseInt(mStr);

		ZZZZGroup group = zzzzMap[zIdx];
		if (group == null) {
			res.cnt = 0;
			return res;
		}

		int count = 0;

		Car[] tempParked = new Car[5];
		int pCount = 0;

		// 1. 주차(parked) 차량 중에서 최대 5대 찾기
		while (count < 5 && !group.parked.isEmpty()) {
			Car c = group.parked.poll();
			if (c.status == 0) {
				res.carlist[count++] = c.carNo;
				tempParked[pCount++] = c; // 나중에 다시 큐로 돌려보낼 차량
			} else if (c.status == 1) {
				group.towed.add(c); // 견인 처리된 차량이면 towed 큐로 이사 보냄!
			}
			// status == 2 인 차량은 버림.
		}

		// 뽑았던 정상 차량 다시 넣기
		for (int i = 0; i < pCount; i++) {
			group.parked.add(tempParked[i]);
		}

		// 2. 5대가 안 채워졌으면 견인(towed) 차량 중에서 마저 찾기
		Car[] tempTowed = new Car[5];
		int tCount = 0;

		while (count < 5 && !group.towed.isEmpty()) {
			Car c = group.towed.poll();
			if (c.status == 1) {
				res.carlist[count++] = c.carNo;
				tempTowed[tCount++] = c;
			} // status == 2 인 차량은 버림
		}

		for (int i = 0; i < tCount; i++) {
			group.towed.add(tempTowed[i]);
		}

		res.cnt = count;
		return res;
	}
}
