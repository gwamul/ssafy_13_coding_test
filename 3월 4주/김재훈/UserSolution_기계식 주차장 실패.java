import java.util.*;

/**
 * 진리 정의
 * 1. remain == m*n - CarInGarageMap 여야함
 * 2. areas의 빈자리는 remain과 일치
 * 3. areas와 areasTakenCarCnt은 각 
 * 4. haveToremoveFromQue의 요소의 value들의 합 + CarInGarageMap 은 carQ의 size와 일치
 */
class UserSolution
{
	
	class CarInfo{
		
		String carNo;
		int time;
		String garageNo; //A000 
		
		CarInfo(String carNo, int time, String garageNo){
			this.carNo = carNo;
			this.time = time;
			this.garageNo = garageNo;
		}
		
		int getXXNum() {
			return Integer.parseInt(carNo.substring(0, 2));
		}
		
		int getYNum() {
			return carNo.charAt(2);
		}
	}

	//생각해보니 굳이 pq로 안해도 됨.
	Queue<CarInfo> carQ;
	
	//이미 차고map에서 지워진 상태에서 다시 들어오면 // 아직 차고 queue에는 남아있어서 중복 문제가 발생
	//map으로 queue에서 지워야할 목록 넣기
	//차량 번호, 몇개 지워야 하는지
	Map<String, Integer> haveToremoveFromQue;
	
	//뒷자리 기준
//	Map<String, PriorityQueue<CarInfo>> back4map;
	
	int n;
	int m;
	int l;
	
	//A~Z까지 주차 구역에 차있는 차 수.
	int[] areasTakenCarCnt;
	//각 구역의 차 유지
	int[][] areas;
	//차고에 남은 자리 수
	int remain;
	//견인된 차량 map 
	Map<String, CarInfo> takenMap;

	//주차된 차량 map
	Map<String, CarInfo> CarInGarageMap;
	
	int time;
	void validate() {
	    int cnt = 0;
	    for (int i = 0; i < n; i++) {
	        int rowCnt = 0;
	        for (int j = 0; j < m; j++) {
	            if (areas[i][j] == 1) rowCnt++;
	        }
	        if (rowCnt != areasTakenCarCnt[i]) {
	            System.out.println("구역 카운트 불일치: " + i);
	        }
	        cnt += rowCnt;
	    }

	    if (cnt != CarInGarageMap.size()) {
	        System.out.println("전체 주차 수 != map size");
	    }

	    if (remain + cnt != n * m) {
	        System.out.println("remain 불일치");
	    }
	}
    public void init(int N, int M, int L)
    {
    	System.out.println("init 들어옴");
    	this.carQ = new ArrayDeque<>();
    	
    	this.n = N;
    	this.m = M;
    	this.l = L;
    	this.time = 0;
    	this.takenMap = new HashMap<>();
    	this.CarInGarageMap = new HashMap<>();
    	this.haveToremoveFromQue = new HashMap<>();
    	areasTakenCarCnt = new int[n];
    	areas = new int[n][m];
    	remain = n*m;
    	validate();
        return;
    }
    
    public int[] findRemainPos() {
    	
    	int indexAlpha = 0;
    	int areaRemainNum = 0;
    	//1. 일단, 가장 많이 남고 ABC기준 순서가 가장 앞인곳 찾기
    	for(int i = 0;i<n;i++) {
    		if(this.m - areasTakenCarCnt[i] > areaRemainNum) {
    			areaRemainNum = this.m - areasTakenCarCnt[i];
    			indexAlpha = i;
    		}
    	}
    	
    	//2. 찾는 곳에서 빈공간 
    	//근데 이거 1000개라고 치면 7천만인데... 좀 애매하긴 하다. 
    	for(int i = 0;i<m;i++) {
    		if(areas[indexAlpha][i] == 0) {
    			return new int[] {indexAlpha, i};
    		}
    	}
    	return new int[] {-1};
    }
    
    public void updateTakenSet(int mTime) {
    	 while(!carQ.isEmpty()) {
         	CarInfo car = carQ.peek();
         	
         	//만약 차량이 이미 출차된 차량이라면 지우고 다시
         	Integer removeCnt = haveToremoveFromQue.get(car.carNo);
         	if(removeCnt != null && removeCnt > 0) {
         		carQ.poll();
         		haveToremoveFromQue.put(car.carNo, removeCnt-1);
         		continue;
         	}
         	
//         	if(CarInGarageMap.get(car.carNo) == null) {
//         		carQ.poll();
//         		continue;
//         	}
         	
         	//만약 차량이 입고된 시간이 L시간이 지났으면 견인
         	if(car.time <= mTime - l) {
         		//일단 차럄 빼고
         		carQ.poll();
         		//견인
         		takenMap.put(car.carNo, car);
         		
         		//주차 구역 비워줘야함.
         		int[] ind = stringToPos(car.garageNo);
         		//해당하는 구역 차량 수 하나 감소
         		areasTakenCarCnt[ind[0]]--;
         		//구역의 차량 비우기
         		areas[ind[0]][ind[1]] = 0;
         		//전체 구역에서 가능한 공간 증가
         		remain++;
         		//주차된 차량 map에서 삭제
         		CarInGarageMap.remove(car.carNo);
         	}
         	else break;
         }
    }

    public Solution.RESULT_E enter(int mTime, String mCarNo)
    {
    	System.out.println("enter("+mTime+", "+mCarNo+")");
        Solution.RESULT_E res_e = new Solution.RESULT_E();
        
        //0. arpq에서 poll하면서 견인되어야 하는 차량들 다 빼기
        updateTakenSet(mTime);
        
        //1. 견인된 차량인가 아닌가
        if(takenMap.get(mCarNo) != null) {
        	//견인 차량이라면 지워주기
        	takenMap.remove(mCarNo);
        }
        
        //2. 구역이 안남았다면
        if(remain == 0) {
            res_e.success = 0;
            res_e.locname = "";
            System.out.println(res_e.success+", "+res_e.locname);
            validate();
            return res_e;
        }
        
        //3. 가장 우선순위가 높은 구역 찾기
        int[] insertPos = findRemainPos();
        if(insertPos[0] == -1) {
        	System.out.println("지금 insertPos부분이 뭔가 잘못됐다. remain이 0인데 빈곳을 못찾음");
        }
        
        
        //4. 해당 위치에 차량 저장
        CarInfo car = new CarInfo(mCarNo, mTime, posToString(insertPos));
        //해당하는 구역 차량 수 하나 증가
		areasTakenCarCnt[insertPos[0]]++;
		//구역의 차량 설정
		areas[insertPos[0]][insertPos[1]] = 1;
		//전체 구역에서 가능한 공간 감소
		remain--;
        //큐에 넣기.
		carQ.add(car);
		//현재 차고 상황 업데이트
		CarInGarageMap.put(mCarNo, car);
        
        res_e.success = 1;
        res_e.locname = posToString(insertPos);
        System.out.println(res_e.success+", "+res_e.locname);
        validate();
        return res_e;
    }

    public int pullout(int mTime, String mCarNo)
    {
    	System.out.println("pullout("+mTime+", "+mCarNo+")");
    	//0. 큐에서 poll하면서 견인되어야 하는 차량들 다 빼기
        updateTakenSet(mTime);
    	
    	//주차되어있는가
        CarInfo curCar = CarInGarageMap.get(mCarNo);
        //주차 안됨.
        if(curCar == null) {
        	//견인도 안되어 있으면
        	if(takenMap.get(mCarNo) == null) {
        		System.out.println(-1);
        		validate();
        		return -1;
        	}
        	else {
        		//견인된 차의 원래 입고 시간
        		int originTime = takenMap.get(mCarNo).time;
        		//견인 차량이라면 지워주기
        		takenMap.remove(mCarNo);
        		//(주차된 기간 + 견인된 기간 * 5) * (-1)을 반환
            	//주차된 기간 : L, 견인된 기간 : mTime - (원래 주차 시간 + L)
        		System.out.println((this.l + (mTime - (originTime + this.l))*5)*(-1));
        		validate();
            	return (this.l + (mTime - (originTime + this.l))*5)*(-1);
        	}
        }
        
    	//주차 된 상황이니 주차된 기간 반환
        //현황 map에서 지워주고
        CarInGarageMap.remove(mCarNo);
        haveToremoveFromQue.put(mCarNo, haveToremoveFromQue.getOrDefault(mCarNo, 0)+1);
        
        //해당하는 구역 차량 수 하나 감소
        int[] pos = stringToPos(curCar.garageNo);
  		areasTakenCarCnt[pos[0]]--;
  		//구역의 차량 설정
  		areas[pos[0]][pos[1]] = 0;
  		//전체 구역에서 가능한 공간 증가
  		remain++;
  		System.out.println(mTime - curCar.time);
  		validate();
        return mTime - curCar.time;
    }
    
    public boolean isMatch(String mStr, String carNo) {
    	String last4 = carNo.substring(3);
    	
    	return mStr.equals(last4);
    }

    public Solution.RESULT_S search(int mTime, String mStr)
    {
    	System.out.println("search("+mTime+", "+mStr+")");
        Solution.RESULT_S res_s = new Solution.RESULT_S();
        res_s.cnt = 0;
        
        PriorityQueue<CarInfo> pq = new PriorityQueue<>((o1, o2) -> {
        	if(o1.getXXNum() != o2.getXXNum()) return o1.getXXNum() - o2.getXXNum();
    		else return o1.getYNum() - o2.getYNum();
        });
        
        //0. 주차된 차량 Queue랑 견인된 차량 목록에서 mStr과 매칭되는 것 정렬하기
        //0-1. 주차된 차량
        Iterator<String> iter = CarInGarageMap.keySet().iterator();
        while(iter.hasNext()) {
        	String key = iter.next();
        	
        	CarInfo car = CarInGarageMap.get(key);
        	if(isMatch(mStr, car.carNo)) {
        		pq.add(car);
        		
        		//요구대로 종료
        		if(pq.size() >= 5) {
        			
        			for(int i = 0;i<5;i++) {
        				CarInfo cur = pq.poll();
        				res_s.carlist[i] = cur.carNo;
        			}
        			res_s.cnt = 5;
        			System.out.println("res_s.cnt : "+res_s.cnt);
        			for(int i = 0;i<res_s.cnt;i++) {
        				System.out.print(res_s.carlist[i]+" ");
        			}
        			System.out.println();
        			validate();
        			return res_s;
        		}
        	}
        }
        
        int size = pq.size();
        
        
        int rest = 5 - size;
        for(int i = 0;i<size;i++) {
			CarInfo cur = pq.poll();
			res_s.cnt = size;
			res_s.carlist[i] = cur.carNo;
		}
        
        Iterator<String> iter2 = takenMap.keySet().iterator();
        while(iter2.hasNext()) {
        	String key = iter2.next();
        	
        	CarInfo car = takenMap.get(key);
        	if(isMatch(mStr, car.carNo)) {
        		pq.add(car);
        		
        		//요구대로 종료
        		if(pq.size() >= rest) {
        			break;
        		}
        	}
        }
        
        int add = pq.size();
        
        for(int i = size;i<size+add;i++) {
			CarInfo cur = pq.poll();
			res_s.carlist[i] = cur.carNo;
		}
        res_s.cnt = size+add;
        System.out.println("res_s.cnt : "+res_s.cnt);
		for(int i = 0;i<res_s.cnt;i++) {
			System.out.print(res_s.carlist[i]+" ");
		}
		System.out.println();
		validate();
        return res_s;
    }
    public String posToString(int[] pos) {
    	char area = (char) (pos[0] + 'A');
    	String num = pos[1]+"";
    	while(num.length() < 3) {
    		num = "0"+num;
    	}
    	return area+num;
    }
    
    public int[] stringToPos(String A000) {
    	int ind1 = A000.charAt(0) - 'A';
    	int ind2 = Integer.parseInt(A000.substring(1));
    	
    	return new int[] {ind1, ind2};
    }
}