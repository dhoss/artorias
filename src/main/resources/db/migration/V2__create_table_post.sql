--
-- Name: post; Type: TABLE; Schema: blog; Owner: artorias; Tablespace:
--

CREATE TABLE post (
    id integer NOT NULL,
    title text NOT NULL,
    body text NOT NULL,
    author integer,
    created_on timestamp with time zone DEFAULT now(),
    updated_on timestamp with time zone DEFAULT now(),
    published_on timestamp with time zone
);


ALTER TABLE post OWNER TO artorias;

--
-- Name: post_id_seq; Type: SEQUENCE; Schema: blog; Owner: artorias
--

CREATE SEQUENCE post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE post_id_seq OWNER TO artorias;

--
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: blog; Owner: artorias
--

ALTER SEQUENCE post_id_seq OWNED BY post.id;


--
-- Name: id; Type: DEFAULT; Schema: blog; Owner: artorias
--

ALTER TABLE ONLY post ALTER COLUMN id SET DEFAULT nextval('post_id_seq'::regclass);


--
-- Name: post_pkey; Type: CONSTRAINT; Schema: blog; Owner: artorias; Tablespace:
--

ALTER TABLE ONLY post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id);