(ns normalizer.utils
  (:require [java-time.api :as jt]
            [clojure.string :as str]
            [java-time.format :as jtf]
            [clj-time.format :as f]
            [clojure.instant :as i]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]))

(def replacement-char "\uFFFD")

(def given-formatter (jtf/formatter "M/d/yy h:mm:ss a"))

(def rfc3339-formatter (jtf/formatter :rfc))

(defn ts->iso-dt [ts]
  (jtf/format (jtf/formatter :iso-date-time) (jtf/parse given-formatter ts)))

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

(defn zip-with-leading
  "Just adds leading zeros in a Clojure-y way"
  [z]
  (let [zstr (str z)]
    (str
     (apply str (repeat (- 5 (count zstr)) "0"))
     zstr)))

(comment

  (hmsms->secs "1:23:32.123")

  (jtf/format (jtf/formatter :iso-date) (jtf/parse given-formatter "03/12/16 11:01:00 PM"))
  (jtf/format (jtf/formatter :iso-date-time) (jtf/parse given-formatter "12/12/16 1:01:00 AM"))

  ;(ts->rfc3339 "3/12/16 11:01:00 PM")



  ;
  )

