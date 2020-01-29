"""
WSGI config for foroplanjwt project.

It exposes the WSGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/2.2/howto/deployment/wsgi/
"""

import os
import sys




path = '/home/techbook/techbook'

if path not in sys.path:
    sys.path.append(path)


os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'techbook.settings')

from django.core.wsgi import get_wsgi_application
application = get_wsgi_application()
