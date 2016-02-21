/**
 *  Switch on when present
 *
 *  Copyright 2016 Joseph
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Switch on when present",
    namespace: "japavao",
    author: "Joseph",
    description: "Switch on switch(es) when present, based on time of day",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Turn on Light after Sunset when Present") {
        input "presence", "capability.presenceSensor", title: "presence", required: true, multiple: true
        input "myswitch", "capability.switch", title: "switch", required: true, multiple: true
    }
}

def installed() {
	subscribe(presence, "presence", myHandler)
    //initialize()
}

def updated() {
	log.info "Updated with settings: ${settings}"
	//unsubscribe()
	//initialize()
}

def initialize() {
	log.info "initialize"
    //checkSunset()
}

def myHandler(evt) {
	log.info "presenceHandler"
  if("present" == evt.value) {
    checkSunset()
  } else {
    myswitch.off()
  }
}

def checkSunset(){
	def now = new Date().getTime()
    def sunrise = getSunriseAndSunset(sunriseOffset: "-00:30")['sunrise'].getTime()
    def sunset = getSunriseAndSunset(sunsetOffset: "-00:30")['sunset'].getTime()
    def afterSunset = now > sunset
    def beforeSunrise = now < sunrise
    
    if(afterSunset || beforeSunrise){
    	log.info "in the if statement"
    	 myswitch.on()
    } else {
    	myswitch.off()	
    }
}

