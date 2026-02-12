
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {

    static List<Team> list;
    static int[] student;
    static int num;
    static List<Integer> [] enemy;

    static class Team{
        int teamNum; //1부터 시작
        Set<Integer> teamMember;

        Team(int teamNum){
            this.teamNum = teamNum;
            teamMember = new HashSet<>();
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        int m = Integer.parseInt(br.readLine().trim());
        list = new ArrayList<>();
        student = new int[n+1];
        num = 1;
        enemy = new List[n+1];
        for(int i = 0;i<=n;i++) {
            enemy[i] = new ArrayList<>();
        }
        for(int i = 0;i<m;i++) {
            String[] line = br.readLine().trim().split(" ");

            if(line[0].equals("F")) {
                AddTeam(Integer.parseInt(line[1]),Integer.parseInt(line[2]));
            }
            else if(line[0].equals("E")) {
                //이후에 원수의 원수를 확인해야 하기 때문에 이중 List로 유지
                int s1 = Integer.parseInt(line[1]);
                int s2 = Integer.parseInt(line[2]);

                enemy[s1].add(s2);
                enemy[s2].add(s1);
            }
        }

        //원수의 원수를 친구로 AddTeam하기
        for(int i = 1;i<=n;i++) {
            List<Integer> enemyList = enemy[i];
            if(enemyList == null) continue;
            for(int j = 0;j<enemyList.size();j++) {
                List<Integer> enemySenemy = enemy[enemyList.get(j)];

                //이제 i와 enemySenemy는 친구
                for(int friend : enemySenemy) {
                    AddTeam(i, friend);
                }
            }
        }

        //혼자 팀인 경우
        int alone = -1; //학생 배열을 0을 안쓰기 때문에
        for(int a : student) {
            if(a == 0) alone++;
        }

        System.out.println(list.size() + alone);
//		System.out.println("alone : "+alone);
//		for(Team t : list) {
//			System.out.println(t.teamMember.toString());
//		}
    }

    private static void AddTeam(int int1, int int2) {
        //1. 이미 팀이 있는지 확인
        //2. 둘다 없는 경우, 한명만 있는 경우, 둘다 있는 경우(같은 팀, 다른팀인 경우) 이렇게 3(+2)가지

        if(student[int1] == 0 && student[int2] == 0) {
            Team newTeam = new Team(num++);
            newTeam.teamMember.add(int1);
            newTeam.teamMember.add(int2);
            list.add(newTeam);
            student[int1] = newTeam.teamNum;
            student[int2] = newTeam.teamNum;
        }
        else if(student[int1] != 0 && student[int2] == 0) {
            Team t = null;
            for(int i = 0;i<list.size();i++) {
                if(list.get(i).teamNum == student[int1]) {
                    t = list.get(i);
                    break;
                }
            }

            t.teamMember.add(int2);
            student[int2] = t.teamNum;
        }
        else if(student[int1] == 0 && student[int2] != 0) {
            Team t = null;
            for(int i = 0;i<list.size();i++) {
                if(list.get(i).teamNum == student[int2]) {
                    t = list.get(i);
                    break;
                }
            }

            t.teamMember.add(int1);
            student[int1] = t.teamNum;
        }
        else if(student[int1] != 0 && student[int2] != 0) {

            if(student[int1] == student[int2]) return;

            //서로 다른 팀에 있는 상황
            Team t1 = null;
            Team t2 = null;
            for(int i = 0;i<list.size();i++) {
                if(list.get(i).teamNum == student[int1]) t1 = list.get(i);
                if(list.get(i).teamNum == student[int2]) t2 = list.get(i);
            }
            //t2팀을 t1에 합치기
            Iterator<Integer> iter = t2.teamMember.iterator();
            while(iter.hasNext()) {
                int stu = iter.next();
                t1.teamMember.add(stu);
                student[stu] = t1.teamNum; //t2에 있던 학생들의 팀 번호 바꾸기
            }
            //t2는 지우기
            list.remove(t2);
        }
    }

}
