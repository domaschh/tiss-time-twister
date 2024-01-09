import subprocess
import sys
from datetime import datetime
from collections import defaultdict

# Function to run a git command and return its output
def run_git_command(cmd):
    return subprocess.check_output(cmd, shell=True).decode('utf-8')

# Function to parse git log and return a list of (date, time) tuples
def get_commit_times(author_name):
    log_format = "%cd"  # Customize this format as needed
    log = run_git_command(f'git log --author="{author_name}" --date=format:"%Y-%m-%d %H:%M:%S" --pretty=format:"{log_format}"')
    return [datetime.strptime(line.strip(), "%Y-%m-%d %H:%M:%S") for line in log.split('\n') if line.strip()]

# Check if the author name is provided
if len(sys.argv) < 2:
    print("Usage: python script.py [author name]")
    sys.exit(1)

author_name = sys.argv[1]

# Group commits by date
commit_times = get_commit_times(author_name)
commits_by_date = defaultdict(list)
for commit_time in commit_times:
    commits_by_date[commit_time.date()].append(commit_time)

# Calculate time spent for each day and sum it
total_time_spent = 0
for date, times in commits_by_date.items():
    if times:
        min_time, max_time = min(times), max(times)
        time_spent = (max_time - min_time).total_seconds() / 3600  # Convert to hours
        total_time_spent += time_spent
        print(f"Date: {date}, Time spent: {time_spent:.2f} hours")

print(f"Total time spent: {total_time_spent:.2f} hours")

