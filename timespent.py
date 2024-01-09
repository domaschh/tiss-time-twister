import subprocess
import sys
from datetime import datetime
from collections import defaultdict
import argparse

# Function to run a git command and return its output
def run_git_command(cmd):
    return subprocess.check_output(cmd, shell=True).decode('utf-8')

# Function to get all distinct authors in the repository
def get_all_authors():
    authors = run_git_command('git log --pretty=format:"%an" | sort | uniq')
    return authors.split('\n')

# Function to parse git log for a specific author and return a list of (date, time) tuples
def get_commit_times(author_name):
    log_format = "%cd"
    log = run_git_command(f'git log --author="{author_name}" --date=format:"%Y-%m-%d %H:%M:%S" --pretty=format:"{log_format}"')
    return [datetime.strptime(line.strip(), "%Y-%m-%d %H:%M:%S") for line in log.split('\n') if line.strip()]

# Function to calculate time spent for each author
def calculate_time_per_author(author_name):
    commit_times = get_commit_times(author_name)
    commits_by_date = defaultdict(list)
    for commit_time in commit_times:
        commits_by_date[commit_time.date()].append(commit_time)

    total_time_spent = 0
    for date, times in commits_by_date.items():
        if times:
            min_time, max_time = min(times), max(times)
            time_spent = (max_time - min_time).total_seconds() / 3600  # Convert to hours
            total_time_spent += time_spent

    return total_time_spent

# Argument parsing
parser = argparse.ArgumentParser()
parser.add_argument("-a", "--all", help="Calculate time for all authors", action="store_true")
parser.add_argument("author", nargs='?', help="Author name to calculate time for")
args = parser.parse_args()

# Main logic
if args.all:
    authors = get_all_authors()
    for author in authors:
        total_time = calculate_time_per_author(author)
        print(f"Author: {author}, Total time spent: {total_time:.2f} hours")
elif args.author:
    total_time = calculate_time_per_author(args.author)
    print(f"Author: {args.author}, Total time spent: {total_time:.2f} hours")
else:
    parser.print_help()

