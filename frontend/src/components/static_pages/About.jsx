import { Container, Row , Col } from "react-bootstrap";
import Navbar from "../general/Navbar";
import Carousel from 'react-bootstrap/Carousel';


function About(){






    return(<div className="text-light">
        <Navbar/>
        
        <Container>
            <Row>
            <Col>
            
            Hector41



            </Col><Col>
            
            Hector41



            </Col><Col>
            
            Hector41



            </Col><Col>
            
            Hector41



            </Col>
            </Row>
            <br/>
            <Row>

                <Col>
                <Carousel >
                    
      <Carousel.Item>

<img src={"https://upload.wikimedia.org/wikipedia/commons/4/44/2004_Vanderbilt-Navy_Game_TE.jpg"} style={{maxWidth:"700px",height:"500px"}}></img>

        <Carousel.Caption>
          <h3>First slide label</h3>
          <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
      <img src={"https://upload.wikimedia.org/wikipedia/commons/4/44/2004_Vanderbilt-Navy_Game_TE.jpg"} style={{maxWidth:"700px",height:"500px"}}></img>

        <Carousel.Caption>
          <h3>Second slide label</h3>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
      <img src={"https://upload.wikimedia.org/wikipedia/commons/4/44/2004_Vanderbilt-Navy_Game_TE.jpg"} style={{maxWidth:"700px",height:"500px"}}></img>

        <Carousel.Caption>
          <h3>Third slide label</h3>
          <p>
            Praesent commodo cursus magna, vel scelerisque nisl consectetur.
          </p>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
                
                </Col>
            </Row>
        </Container>
        
        
        
        





















    </div>);
}
export default About;