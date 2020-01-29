from rest_framework import serializers
from techbook.models import Mathitis, apusies, epidosis, simiosis, links
from django.contrib.auth.models import User
from rest_framework import permissions





class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'username', 'techbook')


class SnippetSerializer(serializers.ModelSerializer):
    #owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        model = Mathitis
        fields = ('onoma','tmima','prosopiko_minima')


class apusies_ser(serializers.ModelSerializer):


    class Meta:
        model = apusies
        fields = ('date_apusias','no_apusion','giamathiti')



class epidosis_ser(serializers.ModelSerializer):


    class Meta:
        model = epidosis
        fields = ('date_epidosis','giamathima','epidosi','eidos')

class simiosis_ser(serializers.ModelSerializer):


    class Meta:
        model = simiosis
        fields = ('date_simiosis','simiosi','giamathiti3')

class links_ser(serializers.ModelSerializer):


    class Meta:
        model = links
        fields = ('date_links','link', 'giamathiti4')



class onoma_ser(serializers.ModelSerializer):


    class Meta:
        model = Mathitis
        fields = ('onoma','tmima','prosopiko_minima')




class onoma_serialiser(serializers.ModelSerializer):


    class Meta:
        model = Mathitis
        fields = ('onoma','tmima','prosopiko_minima')
