import xml.etree.ElementTree as ET
import glob

total = 0
failures = 0
errors = 0
skipped = 0

for pattern in [
    'target/surefire-reports/TEST-*.xml',
    'target/failsafe-reports/TEST-*.xml'
]:
    for f in glob.glob(pattern):
        try:
            root = ET.parse(f).getroot()

            total += int(root.get('tests', 0))
            failures += int(root.get('failures', 0))
            errors += int(root.get('errors', 0))
            skipped += int(root.get('skipped', 0))

        except Exception as e:
            print(f"# Error parsing {f}: {e}")

successful = total - failures - errors - skipped

print(f"tests_total {total}")
print(f"tests_failures {failures}")
print(f"tests_errors {errors}")
print(f"tests_skipped {skipped}")
print(f"tests_successful {successful}")