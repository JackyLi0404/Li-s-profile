from django.contrib import admin
from django.urls import include,path
from .views import login_site,signup_site


urlpatterns = [
    path('admin/', admin.site.urls),  
]

