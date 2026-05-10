import xml.etree.ElementTree as ET
import glob

total = 0
for pattern in [
    'target/surefire-reports/TEST-*.xml',
    'target/failsafe-reports/TEST-*.xml'
]:
    for f in glob.glob(pattern):
        try:
            root = ET.parse(f).getroot()
            total += int(root.get('tests', 0))
        except:
            pass
print(total)