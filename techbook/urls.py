"""foroplanjwt URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from rest_framework_simplejwt import views as jwt_views
from techbook import views, xls
from django.conf.urls import url, include
from rest_framework.authtoken.views import obtain_auth_token


urlpatterns = [
    url(r'^jet/', include('jet.urls', 'jet')),
    url(r'^jet/dashboard/', include('jet.dashboard.urls', 'jet-dashboard')),
    url(r'^admin/', admin.site.urls),
    url(r'^techbook/$', views.stoixeia_detail),


    url(r'^techbook/(?P<pk>[0-9]+)/$', views.onoma_detail),


    #url(r'^apusies/(?P<pk>[0-9]+)/$', views.apusies_detail),

    url(r'^apusies/$', views.apusies_list),
    url(r'^links/$', views.links_list),
    url(r'^epidosis/$', views.epidosis_list),
    url(r'^simiosis/$', views.simiosis_list),
    #url('admin/', admin.site.urls),
    url('api-token-auth/', obtain_auth_token, name='api_token_auth'),
    url('api/token/', jwt_views.TokenObtainPairView.as_view(), name='token_obtain_pair'),
    url('api/token/refresh/', jwt_views.TokenRefreshView.as_view(), name='token_refresh')

]

