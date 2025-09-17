import boto3
from datetime import datetime

def get_all_real_ec2_instances():
    ec2 = boto3.client('ec2', region_name='ap-south-1')  # Change region if needed

    try:
        response = ec2.describe_instances()
        instances_info = []

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

        return instances_info

    except Exception as e:
        print("Error fetching EC2 instances:", str(e))
        return []

# Run directly
if __name__ == "__main__":
    all_instances = get_all_real_ec2_instances()
    if not all_instances:
        print("No EC2 instances found.")
    else:
        for idx, instance in enumerate(all_instances, start=1):
            print(f"\n--- Instance {idx} ---")
            for key, value in instance.items():
                print(f"{key}: {value}")
