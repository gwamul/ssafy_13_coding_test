package week8.기계식주차장;

import java.util.*;

class Car {
    String carNo;
    int xx;
    char y;
    int zzzz;
    int status; // 0: 주차, 1: 견인, 2: 완전 삭제
    int enterTime;
    int row, col;

    Car(String no, int time, int r, int c) {
        this.carNo = no;
        this.xx = (no.charAt(0) - '0') * 10 + (no.charAt(1) - '0');
        this.y = no.charAt(2);
        this.zzzz = (no.charAt(3) - '0') * 1000 + (no.charAt(4) - '0') * 100 
                  + (no.charAt(5) - '0') * 10 + (no.charAt(6) - '0');
        this.status = 0;
        this.enterTime = time;
        this.row = r;
        this.col = c;
    }
}

// [핵심] 큐에 넣기 위한 포장지(스냅샷) 클래스
class Node implements Comparable<Node> {
    Car car;
    int recordedStatus; // 큐에 들어갈 당시의 상태

    Node(Car car, int recordedStatus) {
        this.car = car;
        this.recordedStatus = recordedStatus;
    }

    @Override
    public int compareTo(Node o) {
        // 1. 상태 비교 (주차 0 > 견인 1)
        if (this.recordedStatus != o.recordedStatus) return this.recordedStatus - o.recordedStatus;
        // 2. XX 오름차순
        if (this.car.xx != o.car.xx) return this.car.xx - o.car.xx;
        // 3. Y 오름차순
        return this.car.y - o.car.y; 
    }
}

class UserSolution {
    int n, m, l;
    
    int[] emptyCount; 
    PriorityQueue<Integer>[] emptySlots;
    Queue<Car> presentCars; 
    Map<String, Car> carMap;
    
    // [변경점] Node(포장지)를 담는 단일 PriorityQueue 배열
    PriorityQueue<Node>[] zzzzMap; 

    @SuppressWarnings("unchecked")
    public void init(int N, int M, int L) {
        n = N; m = M; l = L;
        
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
        zzzzMap = new PriorityQueue[10000];
    }

    void simulate(int t) {
        while (!presentCars.isEmpty()) {
            if (presentCars.peek().enterTime + l <= t) {
                Car car = presentCars.poll();

                if (car.status == 0) {
                    car.status = 1; // 실제 차량 상태 변경
                    
                    // [핵심] 견인된 새로운 상태의 포장지를 하나 더 만들어서 큐에 추가! (재정렬됨)
                    if(zzzzMap[car.zzzz] != null) {
                        zzzzMap[car.zzzz].add(new Node(car, 1));
                    }
                    
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
            zzzzMap[newCar.zzzz] = new PriorityQueue<>();
        }
        // [핵심] 처음 입차할 때 '주차(0)' 포장지를 만들어 삽입
        zzzzMap[newCar.zzzz].add(new Node(newCar, 0));
        
        presentCars.add(newCar);

        res.success = 1;
        
        char c1 = (char) (bestX + 'A');
        char c2 = (char) (bestY / 100 + '0');
        char c3 = (char) ((bestY / 10) % 10 + '0');
        char c4 = (char) (bestY % 10 + '0');
        res.locname = new String(new char[]{c1, c2, c3, c4});
        
        return res;
    }

    public int pullout(int mTime, String mCarNo) {
        simulate(mTime);

        if (!carMap.containsKey(mCarNo)) return -1;
        
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

        int zIdx = (mStr.charAt(0) - '0') * 1000 + (mStr.charAt(1) - '0') * 100 
                 + (mStr.charAt(2) - '0') * 10 + (mStr.charAt(3) - '0');
                 
        PriorityQueue<Node> pq = zzzzMap[zIdx];
        if (pq == null) {
            res.cnt = 0;
            return res;
        }

        int count = 0;
        Node[] tempArr = new Node[5];
        
        while (count < 5 && !pq.isEmpty()) {
            Node node = pq.poll();
            
            // [핵심] 유령 노드 거르기! 
            // 1. 이미 출고/삭제된 차이거나 (status == 2)
            // 2. 포장지에 적힌 상태와 진짜 상태가 다르면 (예: 포장지는 0인데 진짜는 1로 견인된 경우)
            if (node.car.status == 2 || node.car.status != node.recordedStatus) {
                continue; // 무시하고 다음 것 뽑기 (알아서 청소됨)
            }
            
            res.carlist[count] = node.car.carNo;
            tempArr[count] = node; // 정상적인 노드만 저장
            count++;
        }

        // 뽑았던 정상 노드만 다시 큐로 반환
        for (int i = 0; i < count; i++) {
            pq.add(tempArr[i]);
        }

        res.cnt = count;
        return res;
    }
}