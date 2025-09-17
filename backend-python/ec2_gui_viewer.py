import tkinter as tk
from tkinter import scrolledtext
import boto3

def get_all_real_ec2_instances():
    ec2 = boto3.client('ec2', region_name='ap-south-1')  # Use your region
    instances_info = []

    try:
        response = ec2.describe_instances()
        for reservation in response['Reservations']:
            for instance in reservation['Instances']:
                instance_data = {
                    'InstanceId': instance.get('InstanceId'),
                    'State': instance.get('State', {}).get('Name'),
                    'InstanceType': instance.get('InstanceType'),
                    'PublicIP': instance.get('PublicIpAddress', 'N/A'),
                    'PrivateIP': instance.get('PrivateIpAddress', 'N/A'),
                    'KeyName': instance.get('KeyName', 'N/A'),
                    'AvailabilityZone': instance.get('Placement', {}).get('AvailabilityZone'),
                    'LaunchTime': instance.get('LaunchTime').strftime('%Y-%m-%d %H:%M:%S'),
                    'Tags': {tag['Key']: tag['Value'] for tag in instance.get('Tags', [])}
                }
                instances_info.append(instance_data)

    except Exception as e:
        instances_info.append({'Error': str(e)})

    return instances_info

def show_instances():
    output.delete("1.0", tk.END)
    instances = get_all_real_ec2_instances()
    if not instances:
        output.insert(tk.END, "No EC2 instances found.")
        return

    for i, inst in enumerate(instances, start=1):
        output.insert(tk.END, f"\n--- Instance {i} ---\n")
        for key, value in inst.items():
            output.insert(tk.END, f"{key}: {value}\n")

# GUI Setup
root = tk.Tk()
root.title("AWS EC2 Instances Viewer")
root.geometry("700x500")

tk.Label(root, text="AWS EC2 Real Instances", font=("Arial", 16)).pack(pady=10)

refresh_button = tk.Button(root, text="Refresh EC2 Instances", command=show_instances)
refresh_button.pack(pady=5)

output = scrolledtext.ScrolledText(root, width=80, height=25)
output.pack(padx=10, pady=10)

# Load data on start
show_instances()

root.mainloop()
