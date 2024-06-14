import max30100
import time
import random
import firebase_admin
from firebase_admin import credentials, db

mx30 = max30100.MAX30100()

mx30.set_mode(max30100.MODE_SPO2)

mx30.set_spo_config()

mx30.set_led_current()

cred = credentials.Certificate("/home/pi/paramed-ff260-firebase-adminsdk-x42on-d913d52265.json")
firebase_admin.initialize_app(cred, {
    'databaseURL' : 'https://paramed-ff260-default-rtdb.firebaseio.com'
})

ref = db.reference('/')

data = {
    'firstname' : 'Michael',
    'lastname' : 'Meyers',
    'contact' : 3455674567,
    'age' : 45,
    'gender' : 'M',
    'street_address' : '453 MillCrook Ave',
    'city' : 'Toronto',
    'postal' : 'L5Z 6J7',
    'province' : 'ON',
    'status' : 'active',
    'oxygen_level' : 0.0,
    'heart_rate' : 0.0,
    'glucose_level' : 0.0,
    'temperature' : 0.0
}

new_data_ref = ref.push(data)
new_data_key = new_data_ref.key

while True:
    data = ref.child(new_data_key).get()

    if data['status'] == "inactive":
        break;
    mx30.read_sensor()
    if(mx30.red > 0 and mx30.ir > 0):
       # print("Raw IR Readings: "+str(mx30.ir))
       # print("Raw Red Readings: "+str(mx30.red))
        print("")
        print("Calculating SPO2....")
        time.sleep(1)
        value = (float)(mx30.red)/(float)(mx30.ir)
        print("Calculated Value: "+str(value*100)+"%")
        print("")

        updateValue = round(value * 100, 1)

        data_to_update = {
            'oxygen_level' : updateValue,
            'heart_rate' : round(random.uniform(50.0, 100.0), 1),
            'glucose_level' : round(random.uniform(50.0, 100.0), 1),
            'temperature' : round(random.uniform(50.0, 100.0), 1)
            }

        ref.child(new_data_key).update(data_to_update)
    else:
        print("Finger not detected")
    time.sleep(1)
