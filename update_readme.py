import json
import os

def generate_readme():
    file_path = 'study_plan.json'

    if not os.path.exists(file_path):
        print(f"❌ '{file_path}' 파일이 없습니다.")
        return

    # 1. JSON 파일 읽기
    with open(file_path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    current_week_title = data['current_week']
    weeks_data = data['weeks']

    # 2. README 헤더 작성
    content = "# 🐢 SW 역량테스트 A형 대비 스터디\n\n"
    content += "매주 정해진 문제를 풀고 Commit 올려주세요!\n\n"

    # 3. 🔥 이번 주 도전 문제 (최상단 노출)
    content += f"## 이번 주 도전 문제 ({current_week_title})\n"
    content += "| 문제 번호 | 문제 이름(링크) | 상태 |\n"
    content += "| :---: | :--- | :---: |\n"

    # 현재 주차 데이터 찾기
    current_problems = []
    for week in weeks_data:
        if week['title'] == current_week_title:
            current_problems = week['problems']
            break
    
    if not current_problems:
        content += "| - | 휴식 주간이거나 설정 오류입니다 | - |\n"
    else:
        for num in current_problems:
            url = f"https://www.acmicpc.net/problem/{num}"
            content += f"| {num} | [문제 보러가기 🚀]({url}) | 🏃 진행중 |\n"
    
    content += "\n---\n\n"

    # 4. 📚 과거 기록 (접이식으로 깔끔하게)
    content += "## 📚 스터디 기록\n"
    
    # 최신 주차가 위로 오게 역순 정렬해서 보여줌
    for week in reversed(weeks_data):
        # 이번 주는 위에서 보여줬으니 스킵
        if week['title'] == current_week_title:
            continue
            
        content += f"<details>\n<summary><b>{week['title']} (클릭해서 보기)</b></summary>\n\n"
        content += "| 문제 번호 | 링크 |\n"
        content += "| :---: | :--- |\n"
        
        for num in week['problems']:
            url = f"https://www.acmicpc.net/problem/{num}"
            content += f"| {num} | [바로가기]({url}) |\n"
        
        content += "\n</details>\n\n"

    # 5. README 저장
    with open('README.md', 'w', encoding='utf-8') as f:
        f.write(content)
    
    print("✅ README.md 업데이트 완료!")

if __name__ == "__main__":
    generate_readme()