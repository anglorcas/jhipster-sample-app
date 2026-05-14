import requests
import time
import os

URL = "https://jhipster-sample-app.onrender.com/management/logfile"
#URL = "http://localhost:8080/management/logfile"
OUTPUT_FILE = "logs/render.log"

# asegura carpeta
os.makedirs(os.path.dirname(OUTPUT_FILE), exist_ok=True)

last_content = ""

def fetch_logs():
    try:
        r = requests.get(URL, timeout=10)
        if r.status_code == 200:
            return r.text
        else:
            return None
    except Exception as e:
        print(f"[ERROR] {e}")
        return None


def append_new_logs(new_data, old_data):
    # simple diff por sufijo
    if not old_data:
        return new_data

    if new_data.startswith(old_data):
        return new_data[len(old_data):]

    # fallback: si cambia todo, escribimos todo
    return new_data


def main():
    global last_content

    print(" Watching remote Spring Boot logfile endpoint...")

    while True:
        data = fetch_logs()

        if data:
            diff = append_new_logs(data, last_content)

            if diff.strip():
                with open(OUTPUT_FILE, "a", encoding="utf-8") as f:
                    f.write(diff)

                print(f"[LOG] +{len(diff)} bytes")

            last_content = data

        time.sleep(5)  # polling cada 2 segundos


if __name__ == "__main__":
    main()