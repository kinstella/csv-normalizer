(ns normalizer.utils
  (:require
   [clojure.string :as str]
   [java-time.format :as jtf]
   [java-time.api :as jt]))

(def replacement-char "\uFFFD")

(def given-formatter (jtf/formatter "M/d/yy h:mm:ss a"))

(def rfc3339-formatter (jtf/formatter "yyyy-MM-dd'T'HH:mm:ssXXX"))

; TODO: log/throw exception on unparseable rows
(defn given-ts->eastern [ds]
  (jtf/format rfc3339-formatter
              (jt/zoned-date-time ; converting Pacifiic to Eastern
               (jt/zoned-date-time ; create a DT with Pacific
                (jt/local-date-time given-formatter ds)
                "US/Pacific") "US/Eastern")))

; TODO: log/throw exception on unparseable rows
(defn hmsms->secs
  "Doing this manually...rounding to nearest second"
  [hmsstr]
  (let [[h m sms] (str/split hmsstr #":")
        [s ms] (str/split sms #"\.")
        h (Integer/parseInt h)
        m (Integer/parseInt m)
        s (Integer/parseInt s)
        ms (Integer/parseInt ms)]
    (Math/round (+ (* h 3600) (* m 60) s (/ ms 1000.0)))))

(defn zip-with-leading [z]
  (format "%05d" (Integer/parseInt z)))

; TODO: more effiicient way to do this? 
(defn valid-unicode? [c]
  (try (.getName (java.lang.Character. c))
       true
       (catch IllegalArgumentException _ false)))

; TODO: ensure that this approach is valid for catching what we want to catch
(defn sanitize-unicode [s]
  (str/join (map (fn [c]
                   (if valid-unicode?
                     c
                     replacement-char)) (seq s))))

(defn wrap-commas [s]
  (if (str/includes? s ",")
    (str "\"" s "\"")
    s))