package saffy_algo.SWEA.D6;

import java.util.*;

public class SWEA25964_기계식주차장_Main {
    int N, M, L;
    Section[] sectionsArray;          
    PriorityQueue<SectionInfo> pq;   
    PriorityQueue<Car> towingQueue; 
    Map<String, Car> carMap;          
    
    // [개편] List 대신 TreeSet을 사용하여 항상 정렬 상태 유지
    Map<String, TreeSet<SearchCar>> searchMap;

    // 검색 전용 가벼운 객체
    class SearchCar implements Comparable<SearchCar> {
        String carNo;
        Car parent; // 실제 데이터 참조

        public SearchCar(String carNo, Car parent) {
            this.carNo = carNo;
            this.parent = parent;
        }

        @Override
        public int compareTo(SearchCar o) {
            // 1. 주차 차량(towed=false)이 견인 차량(towed=true)보다 우선
            if (this.parent.towed != o.parent.towed) {
                return this.parent.towed ? 1 : -1;
            }
            // 2. XX 번호(xxValue) 비교
            if (this.parent.xxValue != o.parent.xxValue) {
                return this.parent.xxValue - o.parent.xxValue;
            }
            // 3. Y 문자(yChar) 비교
            if (this.parent.yChar != o.parent.yChar) {
                return this.parent.yChar - o.parent.yChar;
            }
            // 4. 마지막으로 차 번호 전체 비교 (TreeSet에서 같은 객체로 인식 안 되게 함)
            return this.carNo.compareTo(o.carNo);
        }
    }

    class SectionInfo {
        int id, carCnt, version;
        public SectionInfo(int id, int carCnt, int version) {
            this.id = id; this.carCnt = carCnt; this.version = version;
        }
    }

    class Section {
        int id; char name; int carCnt = 0; int version = 0; 
        PriorityQueue<Integer> slots = new PriorityQueue<>();
        public Section(int id) {
            this.id = id; this.name = (char) ('A' + id);
            for (int i = 0; i < M; i++) slots.offer(i);
        }
    }

    class Car {
        String carNo;
        int startTime, towedTime = -1;
        int sectionId, slotIdx;
        boolean out = false;
        boolean towed = false;
        int xxValue; char yChar;
        SearchCar sCar; // TreeSet 관리용 객체 연결

        public Car(String carNo, int startTime, int sectionId, int slotIdx) {
            this.carNo = carNo; this.startTime = startTime;
            this.sectionId = sectionId; this.slotIdx = slotIdx;
            this.xxValue = (carNo.charAt(0) - '0') * 10 + (carNo.charAt(1) - '0');
            this.yChar = carNo.charAt(2);
        }
    }

    public void init(int N, int M, int L) {
        this.N = N; this.M = M; this.L = L;
        this.sectionsArray = new Section[N];
        this.carMap = new HashMap<>();
        this.searchMap = new HashMap<>();
        this.pq = new PriorityQueue<>((o1, o2) -> {
            if (o1.carCnt != o2.carCnt) return o1.carCnt - o2.carCnt;
            return o1.id - o2.id;
        });
        this.towingQueue = new PriorityQueue<>((o1, o2) -> (o1.startTime + L) - (o2.startTime + L));
        for (int i = 0; i < N; i++) {
            sectionsArray[i] = new Section(i);
            pq.offer(new SectionInfo(i, 0, 0));
        }
    }

    private void processTowing(int mTime) {
        while (!towingQueue.isEmpty() && (towingQueue.peek().startTime + L) <= mTime) {
            Car c = towingQueue.poll();
            if (c.out) continue; 

            // [핵심] TreeSet 내에서의 위치 재정렬을 위해 삭제 후 재삽입
            String last4 = c.carNo.substring(3);
            TreeSet<SearchCar> ts = searchMap.get(last4);
            if (ts != null) ts.remove(c.sCar);

            c.towed = true;
            c.towedTime = c.startTime + L;

            if (ts != null) ts.add(c.sCar);

            Section s = sectionsArray[c.sectionId];
            s.carCnt--; s.version++;
            s.slots.offer(c.slotIdx);
            pq.offer(new SectionInfo(s.id, s.carCnt, s.version));
        }
    }

    public Solution.RESULT_E enter(int mTime, String mCarNo) {
        processTowing(mTime);
        Solution.RESULT_E res = new Solution.RESULT_E();

        Car old = carMap.get(mCarNo);
        if (old != null && !old.out) {
            // 기존 차가 있다면 TreeSet에서 제거
            TreeSet<SearchCar> ts = searchMap.get(mCarNo.substring(3));
            if (ts != null) ts.remove(old.sCar);
            old.out = true;
        }

        Section target = null;
        while (!pq.isEmpty()) {
            SectionInfo info = pq.poll();
            Section real = sectionsArray[info.id];
            if (info.version == real.version) {
                if (real.carCnt < M) { target = real; break; }
                else { res.success = 0; return res; }
            }
        }

        if (target == null) { res.success = 0; return res; }

        int slotIdx = target.slots.poll();
        target.carCnt++; target.version++;

        Car newCar = new Car(mCarNo, mTime, target.id, slotIdx);
        newCar.sCar = new SearchCar(mCarNo, newCar); // 검색용 객체 생성 및 연결
        carMap.put(mCarNo, newCar);
        towingQueue.offer(newCar);
        pq.offer(new SectionInfo(target.id, target.carCnt, target.version));

        searchMap.computeIfAbsent(mCarNo.substring(3), k -> new TreeSet<>()).add(newCar.sCar);

        res.success = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(target.name);
        if (slotIdx < 100) sb.append('0');
        if (slotIdx < 10) sb.append('0');
        sb.append(slotIdx);
        res.locname = sb.toString();
        
        return res;
    }

    public int pullout(int mTime, String mCarNo) {
        processTowing(mTime);
        Car c = carMap.get(mCarNo);
        if (c == null || c.out) return -1;

        // [핵심] 출차 시 TreeSet에서 완전히 제거
        TreeSet<SearchCar> ts = searchMap.get(mCarNo.substring(3));
        if (ts != null) ts.remove(c.sCar);

        int resultCost = 0;
        if (c.towed) {
            resultCost = -1 * (L + 5 * (mTime - c.towedTime));
        } else {
            resultCost = mTime - c.startTime;
            Section s = sectionsArray[c.sectionId];
            s.carCnt--; s.version++;
            s.slots.offer(c.slotIdx);
            pq.offer(new SectionInfo(s.id, s.carCnt, s.version));
        }
        c.out = true;
        return resultCost;
    }

    public Solution.RESULT_S search(int mTime, String mStr) {
        processTowing(mTime);
        Solution.RESULT_S res = new Solution.RESULT_S();
        
        TreeSet<SearchCar> ts = searchMap.get(mStr);
        if (ts == null || ts.isEmpty()) {
            res.cnt = 0;
            return res;
        }

        int count = 0;
        for (SearchCar sc : ts) {
            if (count >= 5) break;
            res.carlist[count++] = sc.carNo;
        }

        res.cnt = count;
        return res;
    }
}