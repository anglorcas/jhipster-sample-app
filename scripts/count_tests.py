import xml.etree.ElementTree as ET
import glob
total = 0
for f in glob.glob('target/surefire-reports/TEST-*.xml'):
    try:
        root = ET.parse(f).getroot()
        total += int(root.get('tests', 0))
    except:
        pass
print(total)