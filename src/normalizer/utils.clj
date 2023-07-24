(ns normalizer.utils
  (:require
   [clojure.string :as str]
   [java-time.format :as jtf]
   [java-time.api :as jt]))

(def replacement-char "\uFFFD")

(def given-formatter (jtf/formatter "M/d/yy h:mm:ss a"))

(def rfc3339-formatter (jtf/formatter "yyyy-MM-dd'T'HH:mm:ss.SSZZZ"))

;; TODO: output to pacific
(defn given-ts->rfc3339 [ds]
  (jtf/format rfc3339-formatter
              (jt/zoned-date-time
               (jt/local-date-time given-formatter ds)
               "UTC-09:00")))

; TODO: accept zoned dt, convert to eastern
(defn given-ts->eastern [ds]
  (jtf/format rfc3339-formatter
              (jt/zoned-date-time
               (jt/zoned-date-time
                (jt/local-date-time given-formatter ds)
                "UTC-08:00") "UTC-05:00")))

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

; TODO: doublecheck this is right
(defn sanitize-utf8 [s]
  (let [utf8-pattern #"[^\x00-\x7F]"]
    (str/replace s utf8-pattern replacement-char)))

(defn wrap-if-comma [s]
  (if (str/includes? s ",")
    (str "\"" s "\"")
    s))
