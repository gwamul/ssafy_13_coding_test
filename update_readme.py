import json
import os

def generate_readme():
    file_path = 'study_plan.json'
    if not os.path.exists(file_path):
        print(f"❌ '{file_path}' 파일이 없습니다.")
        return

    with open(file_path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    # 문제 번호를 넣으면 제목을 반환하는 헬퍼
    prob_map = data.get('problem_data', {})
    def get_title(num):
        return prob_map.get(str(num), "제목 정보 없음")

    content = "# 🐢 SW 역량테스트 B형 대비 스터디\n\n"
    content += "매주 정해진 문제를 풀고 Commit 올려주세요!\n\n"

    # 1. 주차별 스터디 (상단 노출)
    content += "## 📅 주차별 스터디 일정\n"
    for week in data.get('weeks', []):
        is_current = (week['title'] == data['current_week'])
        status_tag = " ⭐ (이번 주)" if is_current else ""
        
        content += f"### {week['title']}{status_tag}\n"
        content += "| 문제 번호 | 문제 이름 | 링크 |\n| :---: | :--- | :---: |\n"
        for num in week['problems']:
            content += f"| {num} | {get_title(num)} | [바로가기](https://www.acmicpc.net/problem/{num}) |\n"
        content += "\n"

    content += "---\n\n"

    # 2. 별도 관리 섹션 (DP 문제 리스트 등)
    if 'extra_topics' in data:
        content += "## 📚 별도 문제\n"
        for item in data['extra_topics']:
            content += f"<details>\n<summary><b>{item['topic']} (펼치기)</b></summary>\n\n"
            content += "| 문제 번호 | 문제 이름 | 링크 |\n| :---: | :--- | :---: |\n"
            for num in item['problems']:
                content += f"| {num} | {get_title(num)} | [바로가기](https://www.acmicpc.net/problem/{num}) |\n"
            content += "\n</details>\n\n"

    with open('README.md', 'w', encoding='utf-8') as f:
        f.write(content)
    
    print("✅ README.md 업데이트 완료! (로컬 데이터 사용)")

if __name__ == "__main__":
    generate_readme()