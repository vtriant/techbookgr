from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework.permissions import IsAuthenticated
from rest_framework import permissions
from techbook.permissions import IsOwnerOrReadOnly
from django.contrib.auth import login, authenticate
from django.contrib.auth.forms import UserCreationForm
from django.shortcuts import render, redirect
from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from techbook.models import Mathitis, apusies, epidosis, simiosis, links
from techbook.serializers import SnippetSerializer , apusies_ser, epidosis_ser,simiosis_ser,links_ser, onoma_ser,onoma_serialiser
from django.http import HttpResponse
import xlrd


class HelloView(APIView):
    permission_classes = (IsAuthenticated,)  # <-- And here
    def get(self, request):
        content = {'message': 'Hello, World!'}
        return Response(content)


def signup(request):

    if request.method == 'POST':
        form = UserCreationForm(request.POST)
        if form.is_valid():
            form.save()
            username = form.cleaned_data.get('username')
            raw_password = form.cleaned_data.get('password1')
            user = authenticate(username=username, password=raw_password)
            login(request, user)
            return redirect('/')
    else:
        form = UserCreationForm()
    return render(request, 'signup.html', {'form': form})


permission_classes = (permissions.IsAuthenticated,permissions.IsAuthenticatedOrReadOnly,IsOwnerOrReadOnly)
@api_view(['GET', 'POST'])
def snippet_list(request, format=None):

    #List all snippets, or create a new snippet.
    if request.method == 'GET':

        snippets = Mathitis.objects.all()
        serializer = SnippetSerializer(snippets, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = SnippetSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'PUT', 'DELETE'])
def snippet_detail(request, pk, format=None):
    #Retrieve, update or delete a snippet instance.
    try:
        snippet = Mathitis.objects.get(pk=pk)
    except Pelatis.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = SnippetSerializer(snippet)
        return Response(serializer.data)

    elif request.method == 'PUT':
        serializer = SnippetSerializer(snippet, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'DELETE':
        snippet.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)






@api_view(['GET'])
def onoma_list(request, format=None):

    if request.method == 'GET':
        current_user = request.user.id
        snippets = Mathitis.objects.all()
        serializer = onoma_ser(snippets, many=True)
        return Response(serializer.data)





@api_view(['GET', 'POST'])
def onoma_detail(request,pk,  format=None):
    #Retrieve, update or delete a snippet instance.
    current_user = request.user



    if request.method == 'GET':


            serializer = onoma_ser(pk)
            return Response(serializer.data)




@api_view(['GET', 'POST'])
def stoixeia_detail(request, format=None):
    #Retrieve, update or delete a snippet instance.
        current_user = request.user.onoma
        snippets = Mathitis.objects.filter(onoma=current_user)
        serializer = onoma_serialiser(snippets, many=True)
        return Response(serializer.data)




#APUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIESAPUSIES


@api_view(['GET'])
def apusies_list(request, format=None):

    if request.method == 'GET':
        current_user = request.user.id
        snippets = apusies.objects.filter(giamathiti=current_user)
        serializer = apusies_ser(snippets, many=True)
        return Response(serializer.data)






@api_view(['GET', 'POST'])
def apusies_detail(request, pk, format=None):
    #Retrieve, update or delete a snippet instance.
    current_user = request.user
    try:
        snippet = Mathitis.objects.get(pk=pk)
        theuser = snippet.giamathiti

    except Esoda.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':

        if current_user == theuser:
            serializer = epidosis_ser(snippet)
            return Response(serializer.data)
        else:
            return Response(status=status.HTTP_404_NOT_FOUND)
#EPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSISEPIDOSIS

@api_view(['GET'])
def epidosis_list(request, format=None):

    if request.method == 'GET':
        current_user = request.user.id
        snippets = epidosis.objects.filter(giamathiti2=current_user)
        serializer = epidosis_ser(snippets, many=True)
        return Response(serializer.data)






@api_view(['GET', 'POST'])
def epidosis_detail(request, pk, format=None):
    #Retrieve, update or delete a snippet instance.
    current_user = request.user
    try:
        snippet = Mathitis.objects.get(pk=pk)
        theuser = snippet.giamathiti2

    except Esoda.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':

        if current_user == theuser:
            serializer = SnippetSerializer2(snippet)
            return Response(serializer.data)
        else:
            return Response(status=status.HTTP_404_NOT_FOUND)


#SIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISISIMIOSISI

@api_view(['GET'])
def simiosis_list(request, format=None):

     if request.method == 'GET':
       # current_user = request.user.id
        snippets = simiosis.objects.filter()
        serializer = simiosis_ser(snippets, many=True)
        return Response(serializer.data)







@api_view(['GET', 'POST'])
def simiosis_detail(request, pk, format=None):
    #Retrieve, update or delete a snippet instance.
    current_user = request.user
    try:
        snippet = Mathitis.objects.get(pk=pk)
        theuser = snippet.giamathiti3

    except Esoda.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':

        if current_user == theuser:
            serializer = simiosis_ser(snippet)
            return Response(serializer.data)
        else:
            return Response(status=status.HTTP_404_NOT_FOUND)



#LINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKSLINKS

@api_view(['GET'])
def links_list(request, format=None):

    if request.method == 'GET':
        current_user = request.user.id
        snippets = links.objects.filter()
        serializer = links_ser(snippets, many=True)
        return Response(serializer.data)






@api_view(['GET', 'POST'])
def links_detail(request, pk, format=None):
    #Retrieve, update or delete a snippet instance.
    current_user = request.user
    try:
        snippet = Mathitis.objects.get(pk=pk)
        theuser = snippet.giamathiti4

    except Esoda.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':

        if current_user == theuser:
            serializer = links_ser(snippet)
            return Response(serializer.data)
        else:
            return Response(status=status.HTTP_404_NOT_FOUND)








