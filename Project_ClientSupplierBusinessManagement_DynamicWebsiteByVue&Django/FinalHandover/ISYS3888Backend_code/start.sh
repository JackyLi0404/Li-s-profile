cd /www/wwwroot/myproject3888 && nohup /home/ec2-user/.local/share/virtualenvs/myproject3888-Bc8-QMrc/bin/gunicorn -w 4 myproject3888.wsgi:application -b 0.0.0.0:8081 > /www/wwwroot/myproject3888/run.log 2>&1 &