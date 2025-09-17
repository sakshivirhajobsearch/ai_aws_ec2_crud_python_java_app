import boto3

ec2 = boto3.client('ec2', region_name='ap-south-1')  # or your preferred region

def create_instance():
    response = ec2.run_instances(
        ImageId='ami-0abcdef1234567890',  # Use a valid AMI ID for your region
        InstanceType='t2.micro',
        MinCount=1,
        MaxCount=1
    )
    instance_id = response['Instances'][0]['InstanceId']
    return instance_id

def list_instances():
    response = ec2.describe_instances()
    instance_list = []
    for reservation in response['Reservations']:
        for instance in reservation['Instances']:
            instance_list.append({
                'InstanceId': instance['InstanceId'],
                'State': instance['State']['Name']
            })
    return instance_list

def terminate_instance(instance_id):
    ec2.terminate_instances(InstanceIds=[instance_id])
    return f"Terminated instance {instance_id}"

def get_all_instance_details():
    response = ec2.describe_instances()
    details = []
    for reservation in response['Reservations']:
        for instance in reservation['Instances']:
            instance_data = {
                'InstanceId': instance.get('InstanceId'),
                'State': instance.get('State', {}).get('Name'),
                'InstanceType': instance.get('InstanceType'),
                'PublicIP': instance.get('PublicIpAddress', 'N/A'),
                'PrivateIP': instance.get('PrivateIpAddress', 'N/A'),
                'LaunchTime': str(instance.get('LaunchTime')),
                'AvailabilityZone': instance.get('Placement', {}).get('AvailabilityZone'),
                'Tags': {tag['Key']: tag['Value'] for tag in instance.get('Tags', [])}
            }
            details.append(instance_data)
    return details
