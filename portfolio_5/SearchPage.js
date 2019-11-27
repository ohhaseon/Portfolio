import React, { Component } from 'react';
import {
  Button,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  TextInput,
  FlatList,
} from 'react-native';
import DataView from './DataView';
import Constants from 'expo-constants';

import firebase from 'firebase';
var config = {
  apiKey: 'AIzaSyBFGleFLxgnhUahU6LYlDpvuV22szEaVeE',
  authDomain: 'project1-79c85.firebaseapp.com',
  databaseURL: 'https://project1-79c85.firebaseio.com',
  projectId: 'project1-79c85',
  storageBucket: 'project1-79c85.appspot.com',
  messagingSenderId: '1021320138247',
  appId: '1:1021320138247:web:f0bb2d40ee10f17640e4a2',
  measurementId: 'G-D3N4R2ERKY',
};
if (!firebase.apps.length) {
  firebase.initializeApp(config);
}

export default class App extends Component {
  readToiletInfo() {
    firebase
      .database()
      .ref('ToiletLocation/')
      .on('value', snap => {
        var items = [];
        var count_yess = 0;
        snap.forEach(child => {
          if (child.val().camera == 'yes') {
            if(this.state.toilet == ''){
              if(this.state.floor == child.val().floor){
                items.push({
                  id: child.key,
                  floor: child.val().floor,
                  toilet: child.val().toilet,
                  camera: child.val().camera,
                  date: child.val().date,
                  detail_loc: child.val().detail_loc,
                });
                count_yess++;
              }
            }if(this.state.floor == ''){
              if(this.state.toilet == child.val().toilet){
                items.push({
                  id: child.key,
                  floor: child.val().floor,
                  toilet: child.val().toilet,
                  camera: child.val().camera,
                  date: child.val().date,
                  detail_loc: child.val().detail_loc,
                });
                count_yess++;
              }
            }if(this.state.floor == '' && this.state.toilet == ''){
                items.push({
                  id: child.key,
                  floor: child.val().floor,
                  toilet: child.val().toilet,
                  camera: child.val().camera,
                  date: child.val().date,
                  detail_loc: child.val().detail_loc,
                });
                count_yess++;
            }if(this.state.floor == child.val().floor && this.state.toilet == child.val().toilet){
                items.push({
                  id: child.key,
                  floor: child.val().floor,
                  toilet: child.val().toilet,
                  camera: child.val().camera,
                  date: child.val().date,
                  detail_loc: child.val().detail_loc,
                });
                count_yess++;
            }
          } 
        });

        this.setState({
          data: items,
          count_yes: count_yess,
          floor: '',
          toilet: '',
        });

      });

  }

  keyExtractor = item => item.id;

  constructor(props) {
    super(props);
    this.state = {
      data: [],
      count_yes: 0,
      refreshing: false,
      floor: '',
      toilet: '',
    };
  }

  _renderItem = ({ item }) => {
    return (
      <DataView
        data1={item.floor}
        data11={item.toilet}
        data2={item.camera}
        data3={item.date}
        data4={item.detail_loc}
      />
    );
  };

  onRefresh = () => {
    this.readToiletInfo();
  };

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.header} />

        <View style={styles.content}>
          <View style={styles.firstView}>
            <Text style={styles.head}>SAFE RESTROOM</Text>
          </View>

          <View style={styles.secondView}>
            <View style={styles.header_loc}>
              <Text style={styles.loc}> LOCATION</Text>
            </View>
            <View style={styles.input}>
              <Text style={styles.word}>input</Text>
            </View>
          </View>

          <View style={styles.blank} />

          <View style={styles.search_view}>
            <View style={styles.blank_start} />
            <TextInput style={styles.input_num} 
              onChangeText={text => this.setState({ floor: text })}
              value={this.state.floor}
              placeholder='#'
              placeholderTextColor='gray'/>
            <Text style={styles.word}> FLOOR</Text>
            <View style={styles.blank_search} />
            <TextInput style={styles.input_num} 
              onChangeText={text => this.setState({ toilet: text })}
              value={this.state.toilet}
              placeholder='#'
              placeholderTextColor='gray'/>
            <Text style={styles.word}> TOILET</Text>
            <View style={styles.blank_start} />
            <Text> (상세 검색) </Text>
          </View>

          <View style={styles.blank} />

          <View style={styles.search_view}>
            <View style={styles.result}>
            <Text style={styles.text}> 현재까지 </Text>
            <Text style={styles.text}> {this.state.count_yes} </Text>
            <Text style={styles.text}> 건의 적발이 있습니다. </Text>
            </View>
            <View style={styles.button_search}>
              <Button title="SEARCH" color="black" onPress={this.onRefresh} />
            </View>
          </View>

          <View style={styles.blank} />

          <View style={styles.thirdView}>
            <View style={styles.profile}>
              <Text style={styles.word_loc}>TOILET</Text>
            </View>
            <View style={styles.line} />
            <View style={styles.profile}>
              <Text style={styles.word_loc}>CAMERA</Text>
            </View>
            <View style={styles.line} />
            <View style={styles.profile_date}>
              <Text style={styles.word_loc}>DATE</Text>
            </View>
          </View>

          <View style={styles.under}>
            <View style={styles.dot} />
            <View style={styles.underline} />
          </View>

          <View style={styles.dataV}>
            <FlatList
              data={this.state.data}
              renderItem={this._renderItem}
              keyExtractor={this.keyExtractor}
              onRefresh={this.onRefresh}
              refreshing={this.state.refreshing}
            />
          </View>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  content: {
    flex: 1,
  },
  header: {
    height: 20,
    backgroundColor: 'white',
  },
  firstView: {
    width: '100%',
    height: 50,
    backgroundColor: 'white',
    alignItems: 'center',
    justifyContent: 'center',
  },
  head: {
    color: '#AAABD3',
    fontSize: 27,
  },
  secondView: {
    width: '100%',
    height: 70,
    alignItems: 'center',
    justifyContent: 'center',
  },
  header_loc: {
    width: '100%',
    height: 35,
    backgroundColor: '#AAABD3',
    alignItems: 'flex-start',
    justifyContent: 'center',
    color: 'black',
  },
  loc: {
    fontSize: 20,
  },
  input: {
    backgroundColor: '#AAABD3',
    width: '100%',
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'row',
  },
  search_view: {
    width: '100%',
    height: 50,
    backgroundColor: '#AAABD3',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
  },
  result:{
    padding: 5,
    width: '75%',
    flexDirection: 'row',
    alignItems: 'flex-start',
    justifyContent: 'flex-start',
  },
  text:{
    fontSize: 19,
  },
  input_num: {
    width: 30,
    height: 40,
    borderColor: 'black',
    borderWidth: 1,
    backgroundColor: 'white',
    padding:3
  },
  blank_start: {
    backgroundColor: '#AAABD3',
    width: '3%',
    height: 50,
  },
  blank_search: {
    backgroundColor: '#AAABD3',
    width: '6%',
    height: 50,
  },
  button_search: {
    width: 90,
    height: 40,
    borderColor: '#AAABD3',
    borderWidth: 1,
    backgroundColor: '#CBA6C3',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 3,
  },
  thirdView: {
    width: '100%',
    height: 50,
    flexDirection: 'row',
    alignItems: 'center',
  },
  profile: {
    width: 100,
    height: '100%',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
  word_loc: {
    fontSize: 15,
  },
  blank: {
    width: '100%',
    height: 3,
    backgroundColor: 'white',
  },
  line: {
    width: 3,
    height: '100%',
    backgroundColor: '#CBA6C3',
    borderTopRightRadius: 5,
    borderTopLeftRadius: 5,
  },
  profile_date: {
    height: '100%',
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
  },
  under: {
    width: '100%',
    height: 3,
    flexDirection: 'row',
  },
  dot: {
    width: '1%',
    height: 1,
    backgroundColor: 'white',
  },
  underline: {
    width: '98%',
    height: 3,
    backgroundColor: '#CBA6C3',
    borderRadius: 5,
  },
  dataV: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
    backgroundColor: 'white',
  },
});
